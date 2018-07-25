package pro.pie.me.dao.mapper;

import pro.pie.me.dao.util.ProcessorUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BeanListRowMapper<T> implements RowMapper<List<T>> {

    private Class<T> requiredType;

    /**
     * Create a new {@code BeanRowMapper} for bean-style configuration.
     *
     * @see #setRequiredType
     */
    public BeanListRowMapper() {
    }

    /**
     * Create a new {@code BeanRowMapper}.
     * <p>Consider using the {@link #newInstance} factory method instead,
     * which allows for specifying the required type once only.
     *
     * @param requiredType the type that each result object is expected to match
     */
    public BeanListRowMapper(Class<T> requiredType) {
        setRequiredType(requiredType);
    }


    /**
     * Set the type that each result object is expected to match.
     * <p>If not specified, the column value will be exposed as
     * returned by the JDBC driver.
     */
    public void setRequiredType(Class<T> requiredType) {
        this.requiredType = requiredType;
    }

    @Override
    public List<T> mapRow(ResultSet rs, int rowNum) throws SQLException {

        return ProcessorUtils.toBeanList(rs, requiredType);
    }
}

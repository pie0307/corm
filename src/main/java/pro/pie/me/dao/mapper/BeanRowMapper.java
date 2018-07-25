package pro.pie.me.dao.mapper;

import pro.pie.me.dao.util.ProcessorUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 对象
 **/
public class BeanRowMapper<T> implements RowMapper<T> {

    private Class<T> requiredType;

    /**
     * Create a new {@code BeanRowMapper} for bean-style configuration.
     *
     * @see #setRequiredType
     */
    public BeanRowMapper() {
    }

    /**
     * Create a new {@code BeanRowMapper}.
     * <p>Consider using the {@link #newInstance} factory method instead,
     * which allows for specifying the required type once only.
     *
     * @param requiredType the type that each result object is expected to match
     */
    public BeanRowMapper(Class<T> requiredType) {
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
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {

        return ProcessorUtils.toBean(rs, requiredType);
    }

    /**
     * Static factory method to create a new {@code BeanRowMapper}
     * (with the required type specified only once).
     *
     * @param requiredType the type that each result object is expected to match
     * @since 4.1
     */
    public static <T> BeanRowMapper<T> newInstance(Class<T> requiredType) {
        return new BeanRowMapper<T>(requiredType);
    }
}

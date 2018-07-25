package pro.pie.me.dao.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties({DruidProperties.class})
public class DataSourceConfig {

    @Resource
    private DruidProperties druidProperties;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        try {
            druidProperties.config(dataSource);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }
}

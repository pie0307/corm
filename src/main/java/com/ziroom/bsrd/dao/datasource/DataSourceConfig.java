package com.ziroom.bsrd.dao.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.ziroom.platform.tesla.druid.filter.CatStatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author chengys4
 *         2017-08-28 17:00
 **/
@Configuration
@EnableConfigurationProperties({DruidProperties.class})
public class DataSourceConfig {

    @Autowired
    private DruidProperties druidProperties;

    /**
     *
     */
    @Bean
    public CatStatFilter catStatFilter() {
        return new CatStatFilter();
    }

    /**
     *
     */
    @Bean
    public DataSource dataSource(CatStatFilter catStatFilter) {
        DruidDataSource dataSource = new DruidDataSource();
        try {
            druidProperties.config(dataSource);
            dataSource.setProxyFilters(Lists.newArrayList(catStatFilter));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }
}

package com.ziroom.bsrd.dao.configuration;

import com.ziroom.bsrd.corm.CService;
import com.ziroom.bsrd.corm.CormConfig;
import com.ziroom.bsrd.corm.mapper.CMapper;
import com.ziroom.bsrd.corm.plugins.SkipInterceptor;
import com.ziroom.bsrd.dao.itf.IDataAccess;
import com.ziroom.bsrd.dao.itf.IFillingDefault;
import com.ziroom.bsrd.dao.itf.IValidate;
import com.ziroom.bsrd.dao.service.DataAccessService;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 数据存储服务配置
 *
 * @author chengys4
 *         2017-12-13 9:32
 **/
@Configuration
@EnableTransactionManagement
public class DataAccessConfig {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ResourceLoader resourceLoader = new DefaultResourceLoader();


    //配置翻页插件
    @Bean
    public Interceptor[] interceptors() {
        return new Interceptor[]{new SkipInterceptor()};
    }

    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setPlugins(interceptors());
        factoryBean.setConfigLocation(resourceLoader.getResource("classpath:mybatis-config.xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory() throws Exception {
        return createSqlSessionFactory(dataSource);
    }

    //配置主从事务管理器
    @Bean
    public DataSourceTransactionManager masterTM() {
        return new DataSourceTransactionManager(dataSource);
    }

    //配置主从Mapper
    @Bean(name = "masterCMapper")
    public MapperFactoryBean<CMapper> masterCMapper() throws Exception {
        MapperFactoryBean<CMapper> mfb = new MapperFactoryBean<>();
        mfb.setMapperInterface(CMapper.class);
        mfb.setSqlSessionFactory(masterSqlSessionFactory());
        return mfb;
    }

    @Bean
    public CormConfig cormConfig() throws Exception {
        CormConfig cormConfig = new CormConfig();
        cormConfig.addDefaultMasterMapper(masterCMapper().getObject());
        return cormConfig;
    }

    @Bean
    public CService cService() throws Exception {
        CService cService = new CService(masterCMapper().getObject());
        return cService;
    }

    @Bean
    public IValidate validate() throws Exception {
        IValidate validatorPlug = new ValidatorPlug();
        return validatorPlug;
    }

    @Bean
    public IFillingDefault fillingDefault() throws Exception {
        IFillingDefault fillingDefault = new DefaultFilling();
        return fillingDefault;
    }

    @Bean
    public IDataAccess dataAccess() throws Exception {
        DataAccessService dataAccessService = new DataAccessService();
        dataAccessService.setcService(cService());
        dataAccessService.setJdbcTemplate(jdbcTemplate);
        dataAccessService.setValidate(validate());
        dataAccessService.setFillingDefault(fillingDefault());
        return dataAccessService;
    }
}

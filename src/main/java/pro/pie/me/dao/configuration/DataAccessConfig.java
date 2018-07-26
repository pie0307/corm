package pro.pie.me.dao.configuration;

import pro.pie.me.constant.DialectEnum;
import pro.pie.me.corm.CService;
import pro.pie.me.corm.CormConfig;
import pro.pie.me.corm.mapper.CMapper;
import pro.pie.me.corm.plugins.SkipInterceptor;
import pro.pie.me.dao.datasource.DruidProperties;
import pro.pie.me.dao.itf.IDataAccess;
import pro.pie.me.dao.itf.IFillingDefault;
import pro.pie.me.dao.itf.IValidate;
import pro.pie.me.dao.service.DataAccessService;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 数据存储服务配置
 **/
@Configuration
@EnableTransactionManagement
public class DataAccessConfig {
    @Resource
    private DataSource dataSource;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    private DruidProperties druidProperties;

    private ResourceLoader resourceLoader = new DefaultResourceLoader();

    //配置翻页插件
    @Bean
    public Interceptor[] interceptors() {
        SkipInterceptor skipInterceptor = new SkipInterceptor();
        skipInterceptor.setDialect(druidProperties.getDialect());
        return new Interceptor[]{skipInterceptor};
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
        DialectEnum dialectEnum = DialectEnum.getDialectEnum(druidProperties.getDialect());
        CService cService = new CService(masterCMapper().getObject(), dialectEnum);
        return cService;
    }

    @Bean
    public IValidate validate() throws Exception {
        IValidate validatorPlug = new ValidatorPlug();
        return validatorPlug;
    }

    @Bean
    public IFillingDefault fillingDefault() throws Exception {
        IFillingDefault fillingDefault = new DefaultFilling(false);
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

# jdbc-spring-boot-starter

自定义jdbc操作的starter

## 如果接入
1. 添加maven依赖

    ```
    <dependency>
        <groupId>com.ziroom.bsrd</groupId>
        <artifactId>jdbc-spring-boot-starter</artifactId>
        <version>1.0.0</version>
    </dependency>

    ```

2. 配置文件中加入如下配置即可使用

 ```
spring.datasource.domian= ip:端口
spring.datasource.databaseName=数据库名
spring.datasource.username=用户
spring.datasource.password=密码
或者

oracle
spring:
  datasource:
    driverClassName: oracle.jdbc.driver.OracleDriver
    domian: 10.16.36.33:1521
    databaseName: resb
    username: hdic
    password: oracle
    validationQuery: SELECT 1 FROM DUAL
    dialect: oracle
    
mysql
spring:
  datasource:
    driverClassName: com.mysql.jdbc.Driver
    domian: 10.16.16.13:3306
    databaseName: resb
    username: bsrd
    password: db
    validationQuery: SELECT x
    dialect: mysql
 ```

 ## 如果使用
 1.Service注入注解
 ```
    @Autowired
    private IDataAccess dataAccess;
 ```

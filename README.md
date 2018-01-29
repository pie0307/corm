# jdbc-spring-boot-starter

自定义jdbc操作的starter


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
spring:
  datasource:
  domian: ip:端口
  databaseName: 数据库名
  username: 用户
  password: 密码
 ```
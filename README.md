# heroku_demoparth
This application is a test showing how to -
* Include JPA in a Spring Application
* Configure Spring security using JPA
* Configure Remember-me functionality in Spring Security

## Configuration
The application has been setup such that the datasource() configuration can be achieved using JNDI or by providing regular database
connection parameters.  
The setting are provided in config.properties (overrideable in the environment variables) and they include -
* database.driver.classname=com.mysql.cj.jdbc.Driver
* database.url=jdbc:mysql://localhost:3306/fresh_fintech?serverTimezone=UTC
* database.username=database_username
* database.password=database_password
* hibernate.hbm2ddl.auto=update
* hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
* jndi.url=java:comp/env/jdbc/ya_web_app_db
* use.jndi=true

## Server Options
1. I have included webapp-runner in the application as it provides an easy way to specify Tomcat as a dependency of your app and launch 
Tomcat. This is achieved by running the following - 
```
java -jar target/dependency/webapp-runner.jar webapp/target/*.war
```

2. Another way to deploy the application is to pick up the webapp-0.0.1-SNAPSHOT file from the target folder of the webapp directory and 
have it deployed in the webapps folder of your local tomcat installation - TOMCAT_HOME/webapps.

3. A final option while developing the application is to include your local tomcat to your IDE (e.g. IntelliJ) and launch directly from the IDE


### Thanks
I hope this explanation is enough to explain how to get up and running with this project. Have fun and feel free to place any comments below.
Thanks once again!

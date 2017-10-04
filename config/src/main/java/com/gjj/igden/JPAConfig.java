package com.gjj.igden;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.gjj.igden.dao")
@ComponentScan({"com.gjj.igden.dao", "com.gjj.igden.service"})
@PropertySource("classpath:config.properties")
public class JPAConfig {

    @Autowired
    Environment env;

    private static final String DRIVER_CLASS_NAME = "database.driver.classname";

    private static final String DATABASE_URL = "database.url";

    private static final String USERNAME = "database.username";

    private static final String PASSWORD = "database.password";

    private static final String HIBERNATE_AUTO_MODE = "hibernate.hbm2ddl.auto";

    private static final String HIBERNATE_DIALECT = "hibernate.dialect";

    private static final String JNDI_URL = "jndi.url";

    private static final String USE_JNDI = "use.jndi";

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    @Bean
    public DataSource dataSource() {
        boolean useJndi = "true".equalsIgnoreCase(env.getProperty(USE_JNDI, "false"));
        System.out.println("Using JNDI? " + useJndi);
        if (useJndi) {
            try {
                return (DataSource) new JndiTemplate().lookup(env.getProperty(JNDI_URL));
            } catch (NamingException ex) {
                throw new IllegalStateException("Problem encountered while connecting to JNDI. Message: " + ex.getMessage());
            }
        } else {
            DriverManagerDataSource datasource = new DriverManagerDataSource();
            datasource.setDriverClassName(env.getProperty(DRIVER_CLASS_NAME));
            datasource.setUrl(env.getProperty(DATABASE_URL));
            datasource.setUsername(env.getProperty(USERNAME));
            datasource.setPassword(env.getProperty(PASSWORD, StringUtils.EMPTY));
            return datasource;
        }
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(new String[]{"com.gjj.igden.model"});

        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(additionalProperties());

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty(HIBERNATE_AUTO_MODE, "update"));
        properties.setProperty("hibernate.dialect", env.getProperty(HIBERNATE_DIALECT, "org.hibernate.dialect.MySQL5Dialect"));
        return properties;
    }

}

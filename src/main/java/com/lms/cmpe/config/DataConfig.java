package com.lms.cmpe.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;

/**
 * Created by akash on 11/12/16.
 */
@Configuration
@PropertySource("classpath:app.properties")
public class DataConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        Resource config = new ClassPathResource("hibernate.cfg.xml");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setConfigLocation(config);
        sessionFactory.setPackagesToScan(env.getProperty("lab2.entity.package"));
        sessionFactory.setDataSource(dataSource());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {

        BasicDataSource ds = new BasicDataSource();

        //Driver class name
        ds.setDriverClassName(env.getProperty("lab2.db.driver"));

        //Set the Url
        ds.setUrl(env.getProperty("lab2.db.url"));

        //set username
        ds.setUsername(env.getProperty("lab2.db.username"));

        //set password
        ds.setPassword(env.getProperty("lab2.db.password"));

        return ds;
    }
}

package com.lms.cmpe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * Created by akash on 11/12/16.
 */
@Configuration
@PropertySource("classpath:app.properties")
public class MailConfig {

    @Autowired
    private Environment env;

 /*   @Bean
    public LocalSessionFactoryBean sessionFactory(){
        Resource config = new ClassPathResource("hibernate.cfg.xml");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setConfigLocation(config);
        sessionFactory.setPackagesToScan(env.getProperty("lab.entity.package"));
        sessionFactory.setDataSource(dataSource());
        return sessionFactory;
    }
*/
 /*   @Bean
    public DataSource dataSource() {

        BasicDataSource ds = new BasicDataSource();

        //Driver class name
        ds.setDriverClassName(env.getProperty("lab.db.driver"));

        //Set the Url
        ds.setUrl(env.getProperty("lab.db.url"));

        //set username
        ds.setUsername(env.getProperty("lab.db.username"));

        //set password
        ds.setPassword(env.getProperty("lab.db.password"));

        return ds;
    }*/

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(env.getProperty("mail.host"));
        javaMailSender.setPort(Integer.parseInt(env.getProperty("mail.port")));
        javaMailSender.setUsername(env.getProperty("mail.username"));
        javaMailSender.setPassword(env.getProperty("mail.password"));

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        /*properties.setProperty("mail.transport.protocol", "smtp");*/
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.auth", "true");
        /*properties.setProperty("mail.debug", "false");*/
        return properties;
    }
}

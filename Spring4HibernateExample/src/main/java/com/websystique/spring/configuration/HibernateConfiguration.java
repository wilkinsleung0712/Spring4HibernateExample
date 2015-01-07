package com.websystique.spring.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 * @Configuration indicates that this class contains one or more bean methods annotated with 
 * @Bean producing beans manageable by spring container.
 *  In our case, this class represent hibernate configuration.
 *  
 * @ComponentScan is equivalent to context:component-scan base-package="..." in xml,
 * providing with where to look for spring managed beans/classes.
 *   
 * @EnableTransactionManagement is equivalent to Spring’s tx:* XML namespace,
 * enabling Spring’s annotation-driven transaction management capability.
 *    
 * @PropertySource is used to declare a set of properties(defined in a properties file
 * in application classpath) in Spring run-time Environment
 * , providing flexibility to have different values in different application environments
 */
@Configuration// indicates that this class contains one or more bean methods annotated with
@EnableTransactionManagement
@ComponentScan({"com.websystique.spring.configuration"})//providing with where to look for spring managed beans/classes.
@PropertySource(value = { "classpath:application.properties" })//link to properties file for application to load the value
public class HibernateConfiguration {
	//autowired environment settings
	@Autowired
	private Environment environment;
	
	
	/*
	 * Method sessionFactory() is creating a LocalSessionFactoryBean, which exactly mirrors the 
	 * XML based configuration : We need a dataSource and hibernate properties (same as 
	 * hibernate.properties). Thanks to @PropertySource, we can externalize the real values in a 
	 * .properties file, and use Spring’s Environment to fetch the value corresponding to an item.
	 * Once the SessionFactory is created, it will be injected into Bean method transactionManager which 
	 * may eventually provide transaction support for the sessions created by this sessionFactory.
	 * 
	 */
	@Bean
	//producing beans manageable by spring container. In our case, this class represent hibernate configuration.
	public LocalSessionFactoryBean sessionFactory(){
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.websystique.spring.model" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	public Properties hibernateProperties() {
		// TODO Auto-generated method stub
		Properties properties=new Properties();
		properties.setProperty("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
		properties.setProperty("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
		properties.setProperty("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
		properties.setProperty("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
		return properties;
	}
	@Bean
	public DataSource dataSource() {
		// TODO Auto-generated method stub
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		
		return dataSource;
	}
	@Bean
    @Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s){
		HibernateTransactionManager  transactionManager = new HibernateTransactionManager ();
		transactionManager.setSessionFactory(s);
		return transactionManager;
	}
}

package com.se.spring.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = "com.se.spring")
@PropertySource({ "classpath:persistence-mssql.properties" })
public class AppConfig implements WebMvcConfigurer {
	@Autowired
	private Environment env;

	private Logger logger = Logger.getLogger(getClass().getName());

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB_INF/view/");
		viewResolver.setSuffix(".jsp");
		return viewResolver;

	}

	@Bean
	public DataSource myDataSource() {
		ComboPooledDataSource myDataSource = new ComboPooledDataSource();
		try {
			myDataSource.setDriverClass(env.getProperty("driver"));
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}

		myDataSource.setJdbcUrl(env.getProperty("url"));
		myDataSource.setUser(env.getProperty("user"));
		myDataSource.setPassword(env.getProperty("password"));

		myDataSource.setInitialPoolSize(getIntProperties("initialPoolSize"));
		myDataSource.setMinPoolSize(getIntProperties("minPoolSize"));
		myDataSource.setMaxPoolSize(getIntProperties("maxPoolSize"));
		myDataSource.setMaxIdleTime(getIntProperties("maxIdleTime"));
		return myDataSource;
	}

	private int getIntProperties(String propName) {
		String propVal = env.getProperty(propName);
		int intPropVal = Integer.parseInt(propVal);
		return intPropVal;
	}

	public Properties getHibernateProperties() {

		Properties prop = new Properties();
		prop.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		prop.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
		return prop;
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

		sessionFactory.setDataSource(myDataSource());
		sessionFactory.setPackagesToScan(env.getProperty("packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());

		return sessionFactory;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		return transactionManager;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

}

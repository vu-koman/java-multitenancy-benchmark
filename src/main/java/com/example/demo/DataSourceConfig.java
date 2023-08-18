package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@ConditionalOnProperty(name = "multitenant.datasource", havingValue = "true")
@Configuration
@EnableJpaRepositories(
		basePackages = "com.example.demo",
		transactionManagerRef = "transcationManager",
		entityManagerFactoryRef = "entityManager")
@EnableTransactionManagement
public class DataSourceConfig {

	@Bean
	@Primary
	@Autowired
	public DataSource dataSource() {
		DataSourceRouting routingDataSource = new DataSourceRouting();
		routingDataSource.initDatasource();
		return routingDataSource;
	}

	@Bean(name = "entityManager")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(
			EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource())
				.packages(Note.class)
				.build();
	}

	@Bean(name = "transcationManager")
	public JpaTransactionManager transactionManager(
			@Autowired @Qualifier("entityManager") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		return new JpaTransactionManager(entityManagerFactoryBean.getObject());
	}
}
package com.ezee.store.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class JdbcConfig {
	@Bean
	public DataSource getConnection() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setPassword("Admin@123");
		dataSource.setUsername("root");
		dataSource.setUrl("jdbc:mysql://localhost:3306/store");

		return dataSource;
	}
	@Bean
	@Scope("prototype")
	public <T> List<T> getList() {
		return new ArrayList<>();
	}
	
}

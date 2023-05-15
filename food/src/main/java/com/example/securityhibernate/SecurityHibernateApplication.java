package com.example.securityhibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class SecurityHibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityHibernateApplication.class, args);
	}

}

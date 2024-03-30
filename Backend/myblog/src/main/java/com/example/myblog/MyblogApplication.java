package com.example.myblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class MyblogApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyblogApplication.class, args);
	}

}

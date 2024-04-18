package com.myblog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyBlogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyBlogAppApplication.class, args);
	}

	@Bean
	public ModelMapper getModel(){
		return new ModelMapper();
	}

}

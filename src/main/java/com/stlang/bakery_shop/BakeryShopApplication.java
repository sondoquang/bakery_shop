package com.stlang.bakery_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class BakeryShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(BakeryShopApplication.class, args);
	}

}

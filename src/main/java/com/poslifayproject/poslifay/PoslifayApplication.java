package com.poslifayproject.poslifay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PoslifayApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoslifayApplication.class, args);
	}

}

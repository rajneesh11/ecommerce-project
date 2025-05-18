package com.ekart.eKartOrderService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EKartOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EKartOrderServiceApplication.class, args);
	}

}

package com.ekart.eKartPaymentService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EKartPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EKartPaymentServiceApplication.class, args);
	}

}

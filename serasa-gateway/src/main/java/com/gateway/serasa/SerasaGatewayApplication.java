package com.gateway.serasa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SerasaGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SerasaGatewayApplication.class, args);
	}

}

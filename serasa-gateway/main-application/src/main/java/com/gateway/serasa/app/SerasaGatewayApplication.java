package com.gateway.serasa.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = {
                "com.gateway.serasa.app",
                "com.gateway.serasa.common"
        }
)
@EntityScan(basePackages = "com.gateway.serasa.common.entity")
@EnableJpaRepositories(basePackages = "com.gateway.serasa.app.repository")
@EnableFeignClients(basePackages = "com.gateway.serasa.externalapi.client")
public class SerasaGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SerasaGatewayApplication.class, args);
    }
}
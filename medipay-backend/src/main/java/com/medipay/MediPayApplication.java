package com.medipay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MediPayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediPayApplication.class, args);
        System.out.println("🏥 MediPay Backend Started Successfully!");
    }
}

package com.ilisi.mstxfleettrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsTxFleetTripApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsTxFleetTripApplication.class, args);
    }

}

package com.bookingblock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.bookingblock.controller",
    "com.bookingblock.model",
    "com.bookingblock.service",
    "com.bookingblock.repository"
})
public class BookingBlockApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookingBlockApplication.class, args);
    }
}


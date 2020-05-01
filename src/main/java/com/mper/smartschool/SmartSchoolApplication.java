package com.mper.smartschool;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class SmartSchoolApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartSchoolApplication.class, args);
    }
}

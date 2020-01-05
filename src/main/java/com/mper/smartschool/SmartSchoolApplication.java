package com.mper.smartschool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.mper.smartschool")
public class SmartSchoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSchoolApplication.class, args);
    }

}

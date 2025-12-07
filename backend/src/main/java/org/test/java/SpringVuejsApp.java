package org.test.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.test.java")
public class SpringVuejsApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringVuejsApp.class, args);
    }
}
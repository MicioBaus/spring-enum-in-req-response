package com.rdas;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class FunctionalWebAppMain {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(FunctionalWebAppMain.class, args);
    }
}


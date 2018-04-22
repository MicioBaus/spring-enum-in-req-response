package com.rdas.testConfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by rdas on 22/04/2018.
 */
@TestConfiguration
@ComponentScan("com.rdas")
public class TestConfig {

    @Bean
    public ObjectMapper objectMapper () {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        return objectMapper;
    }
}

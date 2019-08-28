package com.rdas.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories (basePackages = "com.rdas.repo")
@EntityScan(basePackages = "com.rdas.model")
public class AlbertosDatabaseConfiguration {
}
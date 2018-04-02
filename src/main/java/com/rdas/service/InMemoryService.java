package com.rdas.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class InMemoryService {
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init( ) {
        log.info("Starting a functional Spring 5. {}", applicationContext);
    }


    public void work() {
        log.info("Working");
    }
}

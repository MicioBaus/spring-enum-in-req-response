package com.rdas.config;

import com.rdas.service.InMemoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.ipc.netty.http.server.HttpServer;

import javax.annotation.PostConstruct;

@ConfigurationProperties
@Slf4j
@Configuration
public class ReactiveConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private InMemoryService inMemoryService;

    @Value("${host}")
    private String host;

    @Value("${port}")
    private Integer port;

    @PostConstruct
    public void init( ) {
        log.info("Starting a functional Spring 5. {}", applicationContext);
    }


    @Bean
    RouterFunction<?> routerFunction(){
        return RouterFunctions.route(RequestPredicates.GET("/persons"), request -> null)
                .and(RouterFunctions.route(RequestPredicates.GET("/persons/{id}"), request -> null));
    }

    @Bean
    HttpServer httpServer(RouterFunction<?> routerFunction){
        HttpHandler handler = RouterFunctions.toHttpHandler(routerFunction);
        HttpServer httpServer = HttpServer.create(host, port);
        httpServer.start(new ReactorHttpHandlerAdapter(handler));
        return httpServer;
    }
}

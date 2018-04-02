package com.rdas;

import com.rdas.service.InMemoryService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@ComponentScan("com.rdas")
@ConfigurationProperties
@Slf4j
@SpringBootApplication
public class FunctionalWebAppMain {

    @Autowired
    private InMemoryService inMemoryService;

    @Value("server")
    private String host;

    @Value("port")
    private String port;

    static RouterFunction getRouter() {
        HandlerFunction hello = request -> ok().body(fromObject("Hello"));

        HandlerFunction jsonResp = request -> ServerResponse.ok().body(Mono.just("Hello"), String.class);

        return
                route(
                        GET("/"), hello)
                        .andRoute(
                                GET("/json"), req -> ok().contentType(APPLICATION_JSON).body(fromObject(new Hello("world"))));
    }

    public static void main(String[] args) throws IOException, LifecycleException, InterruptedException {
        new FunctionalWebAppMain().run();
    }

    public void run() throws InterruptedException {
        //log.info("Starting a functional Spring 5. {}: {}", host, port);
        //inMemoryService.work();
        RouterFunction router = getRouter();

        HttpHandler httpHandler = RouterFunctions.toHttpHandler(router);

        HttpServer
                .create("localhost", 8080)
                .newHandler(new ReactorHttpHandlerAdapter(httpHandler))
                .block();

        Thread.currentThread().join();
    }
}

@Data
class Hello {
    private final String name;
}


package com.rdas;

import com.rdas.model.Hello;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

@Slf4j
@SpringBootApplication
public class FunctionalWebAppMain implements CommandLineRunner {

    public static void main(String[] args) throws IOException, LifecycleException, InterruptedException {
        SpringApplication.run(FunctionalWebAppMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting functional Spring 5 non blocking server.");
        RouterFunction router = getRouter();

        HttpHandler httpHandler = RouterFunctions.toHttpHandler(router);

        HttpServer
                .create("localhost", 8080)
                .newHandler(new ReactorHttpHandlerAdapter(httpHandler))
                .block();

        Thread.currentThread().join();
    }

    static RouterFunction getRouter() {
        HandlerFunction textHandler = request -> ok().body(fromObject("Hello"));

        HandlerFunction jsonHandler = request -> ServerResponse.ok().contentType(APPLICATION_JSON).body(Mono.just("RANA DAS"), String.class);

        return
                route(
                        GET("/"), textHandler)
                        .andRoute(
                                GET("/json"), req -> ok().contentType(APPLICATION_JSON).body(fromObject(new Hello("world"))))
                        .andRoute(GET("/s"), jsonHandler);
    }
}


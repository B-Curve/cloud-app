package com.bk.client.router;

import com.bk.client.handler.AppUserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AppUserRouter {

    @Bean
    public RouterFunction<ServerResponse> appUserRoute(AppUserHandler handler) {
        return RouterFunctions
                .nest(RequestPredicates.path("/user"),
                        RouterFunctions.route(GET("/").and(accept(MediaType.APPLICATION_JSON)), handler::findAllUsers)
                        .andRoute(GET("/{email}").and(accept(MediaType.APPLICATION_JSON)), handler::findByEmail)
                        .andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON)), handler::createUser)
                        .andRoute(PUT("/").and(accept(MediaType.APPLICATION_JSON)), handler::updateUser));
    }

}

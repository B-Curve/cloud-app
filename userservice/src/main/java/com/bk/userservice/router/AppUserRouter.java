package com.bk.userservice.router;

import com.bk.userservice.handler.AppUserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AppUserRouter {

    @Bean
    public RouterFunction<ServerResponse> appUserRoute(AppUserHandler handler) {
        return RouterFunctions
                .nest(path("/profile").and(accept(MediaType.APPLICATION_JSON)), innerRoutes(handler));
    }

    private RouterFunction<ServerResponse> innerRoutes(AppUserHandler handler) {
        return RouterFunctions
                .route(GET("/{email}").and(accept(MediaType.APPLICATION_JSON)), handler::getUser)
                .andRoute(POST("/").and(accept(MediaType.APPLICATION_JSON)), handler::createUser);
    }

}

package com.bk.userservice.handler;

import com.bk.userservice.entity.UserReference;
import com.bk.userservice.service.JwtService;
import com.bk.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    public Mono<ServerResponse> getUserByEmail(ServerRequest request) {
        return userService
                .findByEmail(request.pathVariable("email"))
                .flatMap(user -> ServerResponse.ok().body(Mono.just(user), UserReference.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getUserFromToken(ServerRequest request) {
        return jwtService.getJwt(request.headers().firstHeader("Authorization"))
                .map(token -> (String) token.getClaim("sub"))
                .flatMap(email -> userService.findByEmail(email))
                .flatMap(user -> ServerResponse.ok().body(Mono.just(user), UserReference.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

}

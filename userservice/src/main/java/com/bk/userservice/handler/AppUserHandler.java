package com.bk.userservice.handler;

import com.bk.userservice.entity.AppUser;
import com.bk.userservice.service.AppUserService;
import com.bk.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AppUserHandler {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private UserService userService;

    public Mono<ServerResponse> getUser(ServerRequest request) {
        return appUserService
                .getUserByEmail(request.pathVariable("email"))
                .flatMap(user -> ServerResponse.ok().body(Mono.just(user), AppUser.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return appUserService
                .createUser(request.bodyToMono(AppUser.class))
                .doOnSuccess(user -> userService.disableRequiresSetupFlag(user.getEmail()).subscribe())
                .flatMap(user -> ServerResponse.ok().body(Mono.just(user), AppUser.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

}

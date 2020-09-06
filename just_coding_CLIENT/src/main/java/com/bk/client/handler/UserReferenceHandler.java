package com.bk.client.handler;

import com.bk.client.entity.UserReference;
import com.bk.client.exception.UnauthorizedException;
import com.bk.client.service.UserReferenceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class UserReferenceHandler {

    @Autowired
    private UserReferenceService userReferenceService;

    public Mono<ServerResponse> findById(ServerRequest request) {
        Mono<UserReference> userReferenceMono = request
                .principal()
                .cast(UserReference.class)
                .flatMap(u -> {
                    String email = request.pathVariable("email");
                    if (StringUtils.equals(u.getEmail(), email)) {
                        return userReferenceService.getUser(email)
                                .onErrorResume(e -> Mono.just(u));
                    } else {
                        return Mono.error(new UnauthorizedException());
                    }
                });

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                // Force request principal in the case that the account needs to be created.
                .body(userReferenceMono, UserReference.class);
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<UserReference> userReferenceFlux = userReferenceService.getAllUsers();

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userReferenceFlux, UserReference.class);
    }

}

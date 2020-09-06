package com.bk.client.handler;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bk.client.bean.AppUserBean;
import com.bk.client.entity.AppUser;
import com.bk.client.exception.NotFoundException;
import com.bk.client.exception.UnauthorizedException;
import com.bk.client.service.AppUserService;
import com.bk.client.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AppUserHandler {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private JwtService jwtService;

    public Mono<ServerResponse> findAllUsers(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(appUserService.findAll(), AppUserBean.class);
    }

    public Mono<ServerResponse> findByEmail(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(appUserService.findByEmail(request.pathVariable("email")), AppUserBean.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(appUserService.createUser(request.bodyToMono(AppUserBean.class)), AppUserBean.class);
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(null, Object.class);
//        return request.principal()
//                .cast(AppUserBean.class)
//                .map(user -> {
//                    return request.bodyToMono(AppUserBean.class)
//                            .map(u -> {
//                                if (StringUtils.equals(u.getEmail(), user.getEmail())) {
//                                    return u;
//                                } else {
//                                    return new UnauthorizedException(String.format("User with email %s is not authorized to modify user with email %s", user.getEmail(), u.getEmail()));
//                                }
//                            });
//                }).onErrorResume(e -> {
//                    log.debug("Unable to update user", e);
//
//                    return ServerResponse.status(HttpStatus.UNAUTHORIZED)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .body(e.getMessage(), String.class);
//                }).then(ServerResponse.ok()
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body(appUserService.updateUser(request.bodyToMono(AppUserBean.class)), AppUserBean.class));
    }

}

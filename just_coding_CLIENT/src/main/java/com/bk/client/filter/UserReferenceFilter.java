package com.bk.client.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bk.client.bean.AppUserBean;
import com.bk.client.entity.UserReference;
import com.bk.client.exception.NotFoundException;
import com.bk.client.exception.UnauthorizedException;
import com.bk.client.service.JwtService;
import com.bk.client.service.UserReferenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Order(0)
@Component
@Slf4j
public class UserReferenceFilter implements WebFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserReferenceService userReferenceService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        List<String> authorization = exchange.getRequest().getHeaders().get("Authorization");

        if (authorization == null) {
            authorization = Collections.emptyList();
        }

        Optional<String> bearerToken = authorization.stream()
                .filter(a -> a.startsWith("Bearer"))
                .findFirst();

        if (bearerToken.isEmpty()) {
            return Mono.error(new UnauthorizedException());
        }

        DecodedJWT jwt;

        try {
            jwt = jwtService.decodeBearerToken(bearerToken.get());
        } catch (UnauthorizedException e) {
            return Mono.error(new UnauthorizedException());
        }

        String email = jwt.getClaim("sub").asString();

        Mono<UserReference> user = userReferenceService.getUser(email)
                .onErrorResume(e -> {
                    log.debug(String.format("User for email %s does not exist. Creating user...", email));
                    UserReference userReference = new UserReference();
                    userReference.setEmail(email);
                    userReference.setRequiresSetup(true);
                    return userReferenceService.saveUser(Mono.just(userReference));
                });

        return user.flatMap(u -> chain.filter(exchange.mutate().principal(user.cast(Principal.class)).build()));
    }

}

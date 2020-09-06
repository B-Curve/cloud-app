package com.bk.userservice.service;

import com.bk.userservice.exception.BadRequestException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.text.ParseException;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public Mono<JWTClaimsSet> getJwt(String bearerToken) {
        JWTClaimsSet jwt;

        if (StringUtils.isEmpty(bearerToken) || !bearerToken.startsWith("Bearer ")) {
            return Mono.error(new BadRequestException());
        }

        try {
            jwt = JWTParser.parse(bearerToken.split("Bearer ")[1]).getJWTClaimsSet();
        } catch (ParseException e) {
            return Mono.error(new BadRequestException());
        }

        return Mono.just(jwt);
    }

}

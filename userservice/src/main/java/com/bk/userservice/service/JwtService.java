package com.bk.userservice.service;

import com.bk.userservice.exception.BadRequestException;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.text.ParseException;

public interface JwtService {

    Mono<JWTClaimsSet> getJwt(String bearerToken);

}

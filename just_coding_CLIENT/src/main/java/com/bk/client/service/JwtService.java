package com.bk.client.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bk.client.exception.UnauthorizedException;

public interface JwtService {

    DecodedJWT decodeBearerToken(String bearerToken) throws UnauthorizedException;

    String getEmail(String bearerToken) throws UnauthorizedException;

}

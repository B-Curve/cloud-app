package com.bk.client.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bk.client.exception.UnauthorizedException;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    @Override
    public DecodedJWT decodeBearerToken(String bearerToken) throws UnauthorizedException {
        String auth;

        try {
            auth = bearerToken.split("Bearer ")[1];
            return JWT.decode(auth);
        } catch (Exception e) {
            throw new UnauthorizedException("Invalid/Malformed Bearer Token.", e);
        }
    }

    @Override
    public String getEmail(String bearerToken) throws UnauthorizedException {
        return decodeBearerToken(bearerToken).getClaim("sub").asString();
    }

}

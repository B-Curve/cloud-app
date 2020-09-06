package com.bk.client.exception;

import com.bk.client.config.DataBuffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    @Autowired
    private DataBuffer dataBuffer;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }

        if (ex instanceof NotFoundException) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
        } else if (ex instanceof UnauthorizedException) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        } else {
            exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return dataBuffer.write(exchange.getResponse(), ex.getMessage());
    }

}

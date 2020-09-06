package com.bk.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class DataBuffer {

    @Autowired
    private ObjectMapper objectMapper;

    public <T> Mono<Void> write(ServerHttpResponse httpResponse, T object) {
        return httpResponse
                .writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = httpResponse.bufferFactory();
                    try {
                        return bufferFactory.wrap(objectMapper.writeValueAsBytes(object));
                    } catch (Exception ex) {
                        log.warn("Error writing response", ex);
                        return bufferFactory.wrap(new byte[0]);
                    }
                }));
    }

}

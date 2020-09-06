package com.bk.client.service;

import com.bk.client.entity.UserReference;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserReferenceService {

    Mono<UserReference> getUser(String email);

    Flux<UserReference> getAllUsers();

    Mono<UserReference> saveUser(Mono<UserReference> userReferenceMono);

}

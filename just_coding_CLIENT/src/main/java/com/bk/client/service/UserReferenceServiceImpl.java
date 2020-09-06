package com.bk.client.service;

import com.bk.client.entity.UserReference;
import com.bk.client.exception.NotFoundException;
import com.bk.client.repository.UserReferenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserReferenceServiceImpl implements UserReferenceService {

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private UserReferenceRepository userReferenceRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Mono<UserReference> getUser(String email) {
        return userReferenceRepository.findById(email)
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("User with email %s not found", email))));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Flux<UserReference> getAllUsers() {
        return userReferenceRepository.findAll();
    }

    @Override
    public Mono<UserReference> saveUser(Mono<UserReference> userReferenceMono) {
        return userReferenceMono
                .flatMap(userReference -> getUser(userReference.getEmail()))
                .onErrorResume(e -> databaseClient.insert()
                        .into(UserReference.class)
                        .using(userReferenceMono)
                        .then()
                        .flatMap((a) -> userReferenceMono)
                        .flatMap(user -> getUser(user.getEmail())))
                .map(user -> {
                    user.setCreationDate(null);
                    return user;
                })
                .flatMap(userReferenceRepository::save);
    }

}

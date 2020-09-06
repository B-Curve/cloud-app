package com.bk.userservice.service;

import com.bk.userservice.entity.UserReference;
import com.bk.userservice.exception.NotFoundException;
import com.bk.userservice.repository.UserReferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserReferenceRepository userReferenceRepository;

    @Autowired
    private DatabaseClient databaseClient;

    @Override
    public Mono<UserReference> findByEmail(String email) {
        return userReferenceRepository
                .findById(email)
                .switchIfEmpty(databaseClient.insert()
                        .into(UserReference.class)
                        .using(Mono.fromCallable(() -> {
                            UserReference user = new UserReference();
                            user.setEmail(email);
                            user.setRequiresSetup(true);
                            return user;
                        }))
                        .then()
                        .then(userReferenceRepository.findById(email)));
    }

    @Override
    public Mono<Void> disableRequiresSetupFlag(String email) {
        return userReferenceRepository.findById(email)
                .doOnSuccess(user -> {
                    user.setRequiresSetup(false);
                    userReferenceRepository.save(user).subscribe();
                })
                .switchIfEmpty(Mono.error(new NotFoundException(String.format("User with email %s not found.", email))))
                .then();
    }

}

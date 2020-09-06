package com.bk.userservice.service;

import com.bk.userservice.entity.AppUser;
import com.bk.userservice.repository.AppUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private DatabaseClient databaseClient;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public Mono<AppUser> getUserByEmail(String email) {
        return appUserRepository.findById(email);
    }

    @Override
    public Mono<AppUser> createUser(Mono<AppUser> appUserMono) {
        return appUserMono
                .flatMap(user -> getUserByEmail(user.getEmail())
                        .switchIfEmpty(databaseClient.insert()
                        .into(AppUser.class)
                        .using(user)
                        .then()
                        .then(getUserByEmail(user.getEmail()))));
    }

}

package com.bk.userservice.service;

import com.bk.userservice.entity.AppUser;
import reactor.core.publisher.Mono;

public interface AppUserService {

    Mono<AppUser> getUserByEmail(String email);

    Mono<AppUser> createUser(Mono<AppUser> appUserMono);

}

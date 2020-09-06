package com.bk.userservice.service;

import com.bk.userservice.entity.AppUser;
import com.bk.userservice.entity.UserReference;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserReference> findByEmail(String email);

    Mono<Void> disableRequiresSetupFlag(String email);

}

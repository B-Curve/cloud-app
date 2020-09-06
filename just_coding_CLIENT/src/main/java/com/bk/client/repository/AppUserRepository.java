package com.bk.client.repository;

import com.bk.client.entity.AppUser;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface AppUserRepository extends R2dbcRepository<AppUser, Long> {

    Mono<AppUser> findByEmail(String email);

}

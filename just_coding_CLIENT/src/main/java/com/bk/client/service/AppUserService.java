package com.bk.client.service;

import com.bk.client.bean.AppUserBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AppUserService {

    Flux<AppUserBean> findAll();

    Mono<AppUserBean> findByEmail(String email);

    Mono<AppUserBean> createUser(Mono<AppUserBean> appUserBeanMono);

    Mono<AppUserBean> updateUser(Mono<AppUserBean> appUserBeanMono);

}

package com.bk.client.service;

import com.bk.client.bean.AppUserBean;
import com.bk.client.entity.AppUser;
import com.bk.client.exception.NotFoundException;
import com.bk.client.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public Flux<AppUserBean> findAll() {
        return appUserRepository.findAll()
                .map(AppUserBean::new);
    }

    @Override
    public Mono<AppUserBean> findByEmail(String email) {
        return appUserRepository.findByEmail(email)
                .map(AppUserBean::new)
                .switchIfEmpty(Mono.defer(() -> Mono.error(() -> new NotFoundException(
                        String.format("Could not find user with email: %s.", email)))));
    }

    @Override
    public Mono<AppUserBean> createUser(Mono<AppUserBean> appUserBeanMono) {
        return appUserBeanMono
                .map(user -> {
                    AppUser au = new AppUser();
                    au.setPhone(user.getPhone());
                    au.setFirstName(user.getFirstName());
                    au.setLastName(user.getLastName());
                    au.setAge(user.getAge());
                    au.setEmail(user.getEmail());
                    return au;
                })
                .flatMap(appUserRepository::save)
                .map(AppUserBean::new);
    }

    @Override
    public Mono<AppUserBean> updateUser(Mono<AppUserBean> appUserBeanMono) {
        return appUserBeanMono
                .map(user -> {
                    AppUser au = new AppUser();
                    au.setPhone(user.getPhone());
                    au.setFirstName(user.getFirstName());
                    au.setLastName(user.getLastName());
                    au.setAge(user.getAge());
                    return au;
                })
                .flatMap(appUserRepository::save)
                .map(AppUserBean::new);
    }

}

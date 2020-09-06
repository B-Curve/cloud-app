package com.bk.userservice.repository;

import com.bk.userservice.entity.AppUser;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends R2dbcRepository<AppUser, String> {
}

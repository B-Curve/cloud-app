package com.bk.userservice.repository;

import com.bk.userservice.entity.UserReference;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReferenceRepository extends R2dbcRepository<UserReference, String> {



}

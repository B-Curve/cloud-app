package com.bk.client.repository;

import com.bk.client.entity.UserReference;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReferenceRepository extends R2dbcRepository<UserReference, String> {

}

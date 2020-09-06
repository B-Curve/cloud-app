package com.bk.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("APP_USER")
public @Data
@NoArgsConstructor
@AllArgsConstructor
class AppUser {

    @Id
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private String phone;

}


package com.bk.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table("APP_USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String phone;

}

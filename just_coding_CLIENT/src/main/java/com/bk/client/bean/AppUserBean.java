package com.bk.client.bean;

import com.bk.client.entity.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.security.auth.Subject;
import java.security.Principal;

public @Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
class AppUserBean implements Principal {

    public AppUserBean(AppUser appUser) {
        email = appUser.getEmail();
        firstName = appUser.getFirstName();
        lastName = appUser.getLastName();
        age = appUser.getAge();
        phone = appUser.getPhone();
    }

    private String email;
    private String firstName;
    private String lastName;
    private int age;
    private String phone;

    @Override
    public String getName() {
        return email;
    }

    @Override
    public boolean implies(Subject subject) {
        return true;
    }
}

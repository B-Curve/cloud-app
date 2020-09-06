package com.bk.userservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import javax.security.auth.Subject;
import java.security.Principal;
import java.time.LocalDateTime;

@Table("USER_REFERENCE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReference implements Principal {

    @Id
    private String email;
    private LocalDateTime creationDate;
    private boolean requiresSetup;

    @Transient
    @JsonIgnore
    private String name;

    @Override
    public String getName() {
        return email;
    }

    @Override
    public boolean implies(Subject subject) {
        return true;
    }
}

package com.example.loanapp.model;

import jakarta.annotation.Generated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {


    @MongoId
    private String id;
    private String username;
    private String email;
    private String password;
    private Set<ERole> userRoles;
    private Boolean dbStatus = false;

    public User(String username, String email, String password, Set<ERole> userRoles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRoles = userRoles;
    }

    public User(String username, String email) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return dbStatus;
    }
}

package com.example.crowd_funding_backend.security;

import com.example.crowd_funding_backend.model.user.User;
import com.example.crowd_funding_backend.repository.UserDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.Collection;

@Slf4j
public class UserInfoDetails implements UserDetails {


    private String name;
    private String password;

    private List<GrantedAuthority> authorities;

    public UserInfoDetails(User userInfo) {
        this.name = userInfo.getEmail();
        this.password = userInfo.getPassword();
        this.authorities = Collections.emptyList();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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
        return true;
    }
}

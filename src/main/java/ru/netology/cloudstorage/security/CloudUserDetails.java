package ru.netology.cloudstorage.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.netology.cloudstorage.model.entity.UserEntity;

import java.util.Collection;
import java.util.List;

public class CloudUserDetails implements UserDetails {
    private final UserEntity user;

    public CloudUserDetails(UserEntity user) {
        this.user = user;
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    public Long getUserId() {return user.getId();}

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
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
package com.ekart.eKartUserAuthService.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EkartUserDetails implements UserDetails {
    private final String email;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public EkartUserDetails(String email, String password, List<GrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static EkartUserDetails build(EkartUser ekartUser) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Roles role : ekartUser.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return new EkartUserDetails(
                ekartUser.getEmail(),
                ekartUser.getPassword(),
                authorities
        );
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
        return email;
    }
}

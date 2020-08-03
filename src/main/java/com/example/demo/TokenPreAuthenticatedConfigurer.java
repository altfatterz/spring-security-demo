package com.example.demo;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.util.List;

public class TokenPreAuthenticatedConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private AuthenticationManager authenticationManager;

    public TokenPreAuthenticatedConfigurer(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void configure(HttpSecurity httpSecurity) {
        CustomPreAuthenticatedProcessingFilter filter = new CustomPreAuthenticatedProcessingFilter(authenticationManager);
        filter.setAuthenticationDetailsSource(context -> (GrantedAuthoritiesContainer) () -> List.of(new SimpleGrantedAuthority("ROLE_USER")));
        httpSecurity.addFilterAt(filter, AbstractPreAuthenticatedProcessingFilter.class);
    }

}

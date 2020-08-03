package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import javax.servlet.http.HttpServletRequest;

@EnableWebSecurity
@Order(101)
public class ApiSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProblemSupport problemSupport;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // @formatter:off
        http
                .antMatcher("/api/**")
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                    .authenticationEntryPoint(problemSupport)
                    .accessDeniedHandler(problemSupport)
                .and()
                .apply(new TokenPreAuthenticatedConfigurer(authenticationManager()));

        // @formatter:on
    }

    @Bean
    public PreAuthenticatedAuthenticationProvider authenticationProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(new PreAuthenticatedGrantedAuthoritiesUserDetailsService());
        return provider;
    }

}
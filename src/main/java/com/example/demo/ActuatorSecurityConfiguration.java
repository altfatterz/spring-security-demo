package com.example.demo;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Order(100)
public class ActuatorSecurityConfiguration extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity http) throws Exception {

        // @formatter:off
        http
                .antMatcher("/actuator/**")
                .csrf()
                    .disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                    .antMatchers("/actuator/health/**").permitAll()
                    .antMatchers("/actuator/info").permitAll()
                    .antMatchers("/actuator/**").hasRole("ADMIN");
        // @formatter:on

    }

}

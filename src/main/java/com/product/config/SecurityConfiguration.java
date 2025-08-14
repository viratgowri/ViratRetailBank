package com.product.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(value = 11)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()  // ✅ Enable CORS
            .csrf().disable()  // ✅ Disable CSRF for API access
            .authorizeRequests()
            .antMatchers("/**").permitAll() // ✅ Allow all routes (modify as needed)
            .anyRequest().authenticated()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // ✅ Ensure stateless sessions
            .and()
            .headers().frameOptions().disable(); // ✅ Allow if using H2 Database

    }
}

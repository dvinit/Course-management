package com.ltp.gradesubmission.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.ltp.gradesubmission.security.filter.AuthenticationFilter;

//import static org.springframework.security.config.Customizer.withDefaults;
import com.ltp.gradesubmission.security.filter.ExceptionHandlerFilter;
import com.ltp.gradesubmission.security.filter.JWTAuthorizationFilter;
import com.ltp.gradesubmission.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;

//import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@AllArgsConstructor
public class SecurityConfig {

    @Autowired
CustomAuthenticationManager authenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationFilter authfilter = new AuthenticationFilter(authenticationManager);
//authfilter.setFilterProcessesUrl("/authenticate");
        http 
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers(headers -> headers.frameOptions().disable())
            .csrf( csrf -> csrf.disable())
            .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
            .addFilter(authfilter)
            .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
            .authorizeHttpRequests((authorize) -> authorize
                                .antMatchers(SecurityConstants.REGISTER_PATH).permitAll()
                                .antMatchers("/h2/**").permitAll()
                                .antMatchers(HttpMethod.POST, "/login").permitAll()
                                .anyRequest().authenticated()
            );
            
                
        
        return http.build();
    }
    
}
package com.shubham.taskManagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.shubham.taskManagement.service.UserDetailsManagerService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailsManagerService userDetailsManagerService;

    /*
    he SecurityFilterChain is configured to handle incoming requests,
     but it doesn't directly execute code in the SecurityConfiguration class after the initial setup.
     The securityFilterChain method is called during the application startup to configure the security filters,
     not during each request. Therefore, placing a breakpoint in this method won't be triggered by a request.
    * */
    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        //required to disable csrf
        http.csrf(customizer -> customizer.disable());
        //required for requests to be authenticated
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsManagerService);
        authProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return authProvider;
    }


}

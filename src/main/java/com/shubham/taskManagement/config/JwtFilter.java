package com.shubham.taskManagement.config;

import com.shubham.taskManagement.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = "";
        String email = null;

        if (header != null && header.startsWith("Bearer ")) {

            token = header.substring(7);
            email = jwtService.extractEmail(token);

            System.out.println("Extracted email" + email);
        }

        Authentication authObject = SecurityContextHolder.getContext().getAuthentication();

        //if authentication is not yet done , and we are able to find the user.
        if (authObject == null && email != null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            //validate the token
            if (jwtService.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                //when we create token with userDetails, it's considered as authenticated

                //WebAuthenticationDetailsSource is used to populate additional details about the authentication request.
                // Specifically, it is used to build and set the details property of the UsernamePasswordAuthenticationToken.
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }

        }

        filterChain.doFilter(request, response);
    }


}

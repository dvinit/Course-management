package com.ltp.gradesubmission.security.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ltp.gradesubmission.security.SecurityConstants;



public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("We are in jwt auth filter");
                String bearerToken = request.getHeader("Authorization");
if(bearerToken==null || !bearerToken.startsWith(SecurityConstants.BEARER)){
filterChain.doFilter(request,response);
return;
}
System.out.println("Bearer token is good... " + bearerToken);

                String token = bearerToken.replace(SecurityConstants.BEARER, "");
                System.out.println(token);
                String user=  JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();
                System.out.println("Finally here");
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList());
                System.out.println("Even here");
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Authenticated by now probably");
                filterChain.doFilter(request, response);


    }
    

}

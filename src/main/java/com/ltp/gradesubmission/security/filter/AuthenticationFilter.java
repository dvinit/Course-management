package com.ltp.gradesubmission.security.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ltp.gradesubmission.entity.User;
import com.ltp.gradesubmission.security.SecurityConstants;
import com.ltp.gradesubmission.security.manager.CustomAuthenticationManager;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

private CustomAuthenticationManager authenticationManager;

/*@Override
public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
            System.out.println("We are in doFilter...");
    chain.doFilter(request, response);
}*/

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request , HttpServletResponse response) {
 try {
    System.out.println("We are in attemptAuthentication...");
    User user = new ObjectMapper().readValue(request.getInputStream(),User.class) ;

    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
return     authenticationManager.authenticate(authentication);
  } catch (IOException e) {
   throw new RuntimeException();    
} 

    }

@Override
protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
        Authentication authResult) throws IOException, ServletException {
            Algorithm algorithm = Algorithm.HMAC512(SecurityConstants.SECRET_KEY);
    String token =  JWT.create()
                       .withSubject(authResult.getName())
                       .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                       .sign(algorithm);

                    

response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + token);
            System.out.println("Authentication worked ....");
}

@Override
protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException failed) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Incorrect credentials");
    response.getWriter().flush();
            System.out.println("Ohoo Authentication did not work....");
}
    
}

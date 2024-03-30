package com.example.myblog.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.myblog.payload.response.base.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(request.getServletPath());
        if(request.getServletPath().contains("/signin")
           || request.getServletPath().contains("/public")
                || request.getServletPath().equals("/user/register")
        ){
            System.out.println("Hello");
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256("my-secret".getBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedJWT = verifier.verify(token);
                    String email = decodedJWT.getSubject();//subject
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    Map<String, String> errors = new HashMap<>();
                    errors.put("error", exception.getMessage());
                    response.setContentType("application/json");
                    BaseResponse baseResponse = new BaseResponse();
                    baseResponse.setData(errors);
                    baseResponse.setMessage(exception.getMessage());
                    baseResponse.setStatusCode(403);
                    new ObjectMapper().writeValue(response.getOutputStream(), baseResponse);
                }
            } else {
                BaseResponse baseResponse = new BaseResponse();
                baseResponse.setStatusCode(403);
                baseResponse.setMessage("Unauthorized");
                response.setContentType("application/json");
                new ObjectMapper().writeValue(response.getOutputStream(), baseResponse);
            }
        }
    }
}

package com.simple.blogger.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simple.blogger.security.util.JwtUtil;
import com.simple.blogger.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Value("${token.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authorizationHeader = request.getHeader(tokenHeader);

        String username = null;

        if(authorizationHeader != null) {
            username = jwtUtil.extractUsername(authorizationHeader);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userService.loadUserByUsername(username);
            if(jwtUtil.validateToken(authorizationHeader, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        filterChain.doFilter(request, response);
        
    }
    
}

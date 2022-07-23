package com.simple.blogger.security;

import java.util.Arrays;

import com.simple.blogger.security.filter.JwtRequestFilter;
import com.simple.blogger.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

    private UserService userService;
    private Encoder encoder;
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfiguration(UserService userService, Encoder encoder, JwtRequestFilter jwtRequestFilter) {
        this.userService = userService;
        this.encoder = encoder;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(encoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
            .antMatchers("/user/*").permitAll()
            .antMatchers("/h2-console/**").permitAll()
            .anyRequest().authenticated().and().sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().frameOptions().disable();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration().applyPermitDefaultValues();
        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }

}

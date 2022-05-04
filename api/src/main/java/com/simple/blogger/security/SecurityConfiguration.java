package com.simple.blogger.security;

import com.simple.blogger.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

    private UserService userService;
    private Encoder encoder;

    @Autowired
    public SecurityConfiguration(UserService userService, Encoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/user/*").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/").hasAnyRole("USER", "ADMIN")
            .antMatchers("/blog/*").hasAnyRole("USER", "ADMIN")
            .and().formLogin()
            .loginPage("/login").permitAll()
            .usernameParameter("username").passwordParameter("password")
            .and().cors().and().csrf().disable()
            .headers().frameOptions().disable()
            .and().authenticationProvider(authProvider());
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(encoder);
        return authProvider;
    }

}

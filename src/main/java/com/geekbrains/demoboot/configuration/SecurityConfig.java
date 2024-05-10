package com.geekbrains.demoboot.configuration;

import com.geekbrains.demoboot.entities.User;
import com.geekbrains.demoboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                    auth -> auth
                    .requestMatchers("demo/products/add").hasAnyRole("ADMIN")
                    .requestMatchers("demo/products/edit/**").hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
                );
    }

    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    protected BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
}
////    private DataSource dataSource;
////
////    @Autowired
////    public void setDataSource(DataSource dataSource) {
////        this.dataSource = dataSource;
////    }
////
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // (1)
////        auth.jdbcAuthentication().dataSource(dataSource);
////    }
////
//////    @Override
//////    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // (2)
//////        User.UserBuilder users = User.withDefaultPasswordEncoder();
//////        auth.inMemoryAuthentication()
//////                .withUser(users.username("user1").password("pass1").roles("USER", "ADMIN"))
//////                .withUser(users.username("user2").password("pass2").roles("USER"));
//////    }
////
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http.authorizeRequests()
////                .anyRequest().permitAll()
////                .antMatchers("/secured/**").hasAnyRole("ADMIN")
////                .and()
////                .formLogin()
//////                .loginPage("/login")
//////                .loginProcessingUrl("/authenticateTheUser")
////                .permitAll();
////    }
//


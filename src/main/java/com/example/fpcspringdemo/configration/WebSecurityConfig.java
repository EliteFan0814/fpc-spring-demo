package com.example.fpcspringdemo.configration;

import com.example.fpcspringdemo.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
//    private UserService userService;
//
//    @Inject
//    public WebSecurityConfig(UserService userService) {
//        this.userService = userService;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/", "/home", "/js/**", "/login.html", "/register.html", "/auth/**").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin((form) -> form.loginPage("/login").loginProcessingUrl("doLogin").permitAll())
                .logout((logout) -> logout.permitAll());
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
//    }
}

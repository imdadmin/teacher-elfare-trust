package com.khaledmosharraf.twtms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Function;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager createInMemoryUserDetailsManager (){

        UserDetails userDetails1 = createUser("admin", "pass", new String[]{"ADMIN", "USER"});
        UserDetails userDetails2 = createUser("khaled", "pass2", new String[]{"USER"});

        return new InMemoryUserDetailsManager(userDetails1,userDetails2);
    }

    private UserDetails createUser(String username, String password, String[] roles) {
        Function<String, String> encoder
                = input->passwordEncoder().encode(input);
        return User.builder()
                .passwordEncoder(encoder)
                .username(username)
                .password(password)
                .roles(roles)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth->auth.anyRequest().authenticated()
        );
        http.formLogin(withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.headers(header->header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        return http.build();
    }

}

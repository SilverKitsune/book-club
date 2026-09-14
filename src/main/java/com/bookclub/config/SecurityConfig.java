package com.bookclub.config;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import com.bookclub.view.LoginView;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/**").permitAll()
        );
        super.configure(http);
        setLoginView(http, LoginView.class);
        http.formLogin(form -> form.defaultSuccessUrl("/main", true));
        http.csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**")
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

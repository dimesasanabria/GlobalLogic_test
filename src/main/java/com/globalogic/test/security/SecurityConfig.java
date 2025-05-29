package com.globalogic.test.security;
/**  
 * SecurityConfig.java
 * This class configures the security settings for the application.
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
     protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
            http.headers().frameOptions().disable();
            http.authorizeRequests()
            .antMatchers("/api/usuarios/**").permitAll()
            .antMatchers("/h2-console/**").permitAll() // Allow access to the user list API
             // Allow access to the user API
            .anyRequest().authenticated() // Require authentication for any other request
            .and().authorizeRequests();
    }
} 



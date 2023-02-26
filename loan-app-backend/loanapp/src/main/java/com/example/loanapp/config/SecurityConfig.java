// This is a configuration class for Spring Security
package com.example.loanapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    // Define a BCryptPasswordEncoder bean for password encoding
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Define an AuthenticationManager bean for authentication
    @Bean
    AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Define a SecurityFilterChain bean for configuring the security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf -> csrf.disable()) // Disable CSRF protection
                .authorizeHttpRequests(auth ->{
                    // Permit access to the login and registration pages, as well as static resources
                    auth.requestMatchers("/api/v1/login/**").permitAll();
                    auth.requestMatchers("/api/v1/register*/**").permitAll();
                    auth.requestMatchers("/api/v1/loanStatusCheck**","/api/v1/creditResult.html","/resources/**","/assets/**",
                                                    "/js/**","/css/**","/static/**", "/templates/**","/*.css/**", "/*.js/**","/images/**", "/icon/**").permitAll();
                    auth.anyRequest().authenticated(); // Require authentication for all other requests
                    try {
                        // Specify the login page and default success URL
                        auth.and().formLogin()
                                .loginPage("/api/v1/login")
                                .defaultSuccessUrl("/api/v1/loan",true);
                        auth.and().logout()
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID");
                        auth.and().headers().contentTypeOptions().disable();

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                })
                .build();
    }

}

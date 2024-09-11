/*
package com.yogesh.scalermsprojectyogesh.user.security;

import com.yogesh.scalermsprojectyogesh.user.service.JwtAuthorizationFilter;
import com.yogesh.scalermsprojectyogesh.user.service.UserMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableWebMvc
public class JWTSecurityConfig{

    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public JWTSecurityConfig(JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

//    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity in this example
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/financialManagement/welcome","/user/registerUser", "/user/generateToken").permitAll()  // Public endpoints
//                        .requestMatchers("/user/updateUser").hasRole("USER")  // Admin-specific endpoints
                        .anyRequest().permitAll()  // All other endpoints require authentication
                )
                .httpBasic();  // Or JWT-based authentication if using JWT

        return http.build();
        */
/*http.cors().disable();
        http.csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/financialManagement/welcome","/user/registerUser", "/user/generateToken")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/user/updateUser","/user/{id}", "/user/{username}")
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();*//*

        */
/*http.cors().disable();
        http.csrf().disable();
        http.authorizeRequests(authorize -> authorize.requestMatchers("/financialManagement/welcome","/user/registerUser").permitAll());
        http.authorizeRequests(authorize -> authorize.anyRequest().authenticated());
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();*//*

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserMasterService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
*/

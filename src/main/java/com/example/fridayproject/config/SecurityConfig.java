package com.example.fridayproject.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .userDetailsService(userDetailsService)
            .authorizeHttpRequests(r -> {
                r.requestMatchers("/style.css","/login", "/auth", "/register").permitAll();
                r.anyRequest().authenticated();
            })
            .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/view")
                    .permitAll()
            )
            .logout(logout -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
            )
            .authenticationProvider(authenticationProvider)
            .oauth2Login(oauth2Login -> oauth2Login
                    .loginPage("/login")
                    .defaultSuccessUrl("/view")
                    .permitAll()
                    .authorizationEndpoint(authorization -> authorization
                            .authorizationRequestRepository(new HttpSessionOAuth2AuthorizationRequestRepository())
                            .baseUri("/login/oauth2/authorization")
                    )
            );
        return http.build();
    }
}
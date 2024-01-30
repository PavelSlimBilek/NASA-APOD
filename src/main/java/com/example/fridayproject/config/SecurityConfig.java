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
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final HandlerMappingIntrospector mvcHandlerMappingIntrospector;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(customizer -> customizer.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .authorizeRequests(requests -> requests
                        .requestMatchers(new MvcRequestMatcher(mvcHandlerMappingIntrospector, "/register")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(mvcHandlerMappingIntrospector, "/style.css")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(mvcHandlerMappingIntrospector, "/login/**")).permitAll()
                        .requestMatchers(new MvcRequestMatcher(mvcHandlerMappingIntrospector, "/auth/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/view")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
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

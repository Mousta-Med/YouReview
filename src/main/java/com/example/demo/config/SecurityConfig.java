package com.example.demo.config;

import com.example.demo.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return new ProviderManager(provider);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .requestMatchers(getOpenedResources()).permitAll()
                            .requestMatchers("/review/**").hasAuthority("ROLE_USER")
                            .requestMatchers("/reviews/**").hasAuthority("ROLE_ADMIN")
                            .anyRequest().authenticated();
                })
                .formLogin((form) -> form
                        .loginPage("/")
                        .usernameParameter("username")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
                                response.sendRedirect("/review");
                            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                                response.sendRedirect("/reviews");
                            } else {
                                response.sendRedirect("/home");
                            }
                        })
                        .permitAll()
                )
                .logout((logout) -> {
                    logout
                            .permitAll()
                            .logoutUrl("/logout")
                            .logoutSuccessUrl("/");
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    private String[] getOpenedResources() {
        return new String[]{
//                "/**",
                "/h2-console/**",
                "/swagger-ui/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/v3/api-docs",
                "/v3/api-docs/**"
        };
    }
}

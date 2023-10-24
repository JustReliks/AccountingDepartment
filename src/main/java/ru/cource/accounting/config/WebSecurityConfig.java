package ru.cource.accounting.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.cource.accounting.security.jwt.AuthTokenFilter;
import ru.cource.accounting.utils.RoleAuthorities;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    private static final String[] WHITE_LIST_URL = {"/api/registration", "/api/auth/**", "/api/auth"};

    private final AuthTokenFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL).permitAll()

                        .requestMatchers("/api/test/editor").hasAuthority(RoleAuthorities.EDITOR.name())
                        .requestMatchers("/api/test/viewer").hasAuthority(RoleAuthorities.VIEWER.name())
                        .requestMatchers("/api/test/any").hasAnyAuthority(RoleAuthorities.VIEWER.name(),
                                RoleAuthorities.EDITOR.name())
                        .requestMatchers("/api/projects/load").hasAnyAuthority(RoleAuthorities.VIEWER.name(),
                                RoleAuthorities.EDITOR.name())
                        .requestMatchers("/api/projects/create").hasAuthority(RoleAuthorities.EDITOR.name())
                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}

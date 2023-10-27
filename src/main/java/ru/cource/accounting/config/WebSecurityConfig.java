package ru.cource.accounting.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.cource.accounting.security.jwt.AuthTokenFilter;
import ru.cource.accounting.security.jwt.JwtCorsFilter;
import ru.cource.accounting.utils.RoleAuthorities;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class WebSecurityConfig {

    private static final String[] WHITE_LIST_URL = {"/api/registration", "/api/auth/**", "/api/auth"};

    private final AuthTokenFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final JwtCorsFilter filterCors;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()
                .authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL).permitAll()

                        .requestMatchers("/api/test/editor").hasAuthority(RoleAuthorities.EDITOR.name())
                        .requestMatchers("/api/test/viewer").hasAuthority(RoleAuthorities.VIEWER.name())
                        .requestMatchers("/api/test/any").hasAnyAuthority(RoleAuthorities.VIEWER.name(),
                                RoleAuthorities.EDITOR.name())
                        .requestMatchers("/api/projects/**").hasAnyAuthority(RoleAuthorities.VIEWER.name(),
                                RoleAuthorities.EDITOR.name())
                        .requestMatchers("/api/departments/**").hasAnyAuthority(RoleAuthorities.VIEWER.name(),
                                RoleAuthorities.EDITOR.name())

                        .anyRequest()
                        .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

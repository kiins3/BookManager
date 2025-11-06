package com.bookmanager.Config;

import com.bookmanager.Exception.ErrorCode;
import com.bookmanager.Exception.RException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtConfig jwtConfig;

    //private final String[] ENDPOINTS = {"/book/**","/users/**","/login/**","/library/**"};
    private final String[] PUBLIC_ENDPOINT = {
            "/login/token",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
            "/api-docs/**"
    };
    private final String[] USER_ENDPOINT = {"/books/getallbooks", "/books/getbookbytitle/**"
            ,"/booktitles/getallbooktitle","/booktitles/getbooktitlebytitle/**","/library/borrowbook"
            ,"/library/compensation","/library/userreturnbook","/users/myinfo","/library/my-borrows"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .securityMatcher("/**")
                .authorizeHttpRequests(request ->
                request.requestMatchers("/**").permitAll()
                        .requestMatchers(USER_ENDPOINT)
                        .access((auth, context) -> {
                            var authorities = auth.get().getAuthorities();
                            boolean isActiveUser = authorities.stream().anyMatch(a ->
                                    a.getAuthority().equals("ROLE_USER") &&
                                            authorities.stream().anyMatch(b -> b.getAuthority().equals("STATUS_ACTIVE")));
                            boolean isAdminOrLibrarian = authorities.stream().anyMatch(a ->
                                    a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_LIBRARIAN"));
                            return new AuthorizationDecision(isActiveUser || isAdminOrLibrarian);
                        })

                        .requestMatchers("/books/**","/booktitles/**","/library/**").hasAnyAuthority("ROLE_LIBRARIAN","ROLE_ADMIN")
                        .requestMatchers("/users/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated());

        httpSecurity.exceptionHandling(ex -> ex
                .authenticationEntryPoint((request, response, authException) -> {
                    RException exception = new RException(ErrorCode.UNAUTHENTICATED);
                    WriteErrorResponse.writeErrorResponse(response, exception);
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    RException exception = new RException(ErrorCode.FORBIDDEN);
                    WriteErrorResponse.writeErrorResponse(response, exception);
                })
        );

        httpSecurity.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )
        );

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec key = new SecretKeySpec(jwtConfig.getSecretKey().getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS512).build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            String status = jwt.getClaim("status");
            String email = jwt.getClaim("email");
            String role = jwt.getClaimAsString("role");
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (role != null) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }

            if (status != null) {
                authorities.add(new SimpleGrantedAuthority("STATUS_" + status));
            }

            if (email != null) {
                authorities.add(new SimpleGrantedAuthority("EMAIL_" + email));
            }
            return authorities;
        });
        return converter;
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}

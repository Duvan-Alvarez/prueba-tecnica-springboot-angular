package com.makers.loans.security;

import org.springframework.context.annotation.*; import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration @EnableMethodSecurity public class SecurityConfig {
 private final JwtAuthenticationFilter jwt; public SecurityConfig(JwtAuthenticationFilter jwt){this.jwt=jwt;}
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain filterChain(HttpSecurity http)throws Exception{return http.csrf(csrf->csrf.disable()).cors(cors->{}).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/h2-console/**").permitAll().requestMatchers("/api/admin/**").hasRole("ADMIN").anyRequest().authenticated()).headers(h->h.frameOptions(f->f.sameOrigin())).addFilterBefore(jwt,UsernamePasswordAuthenticationFilter.class).build();}
 @Bean org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource(){var c=new org.springframework.web.cors.CorsConfiguration();c.setAllowedOrigins(java.util.List.of("http://localhost:4200"));c.setAllowedMethods(java.util.List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));c.setAllowedHeaders(java.util.List.of("*"));c.setAllowCredentials(false);var s=new org.springframework.web.cors.UrlBasedCorsConfigurationSource();s.registerCorsConfiguration("/**",c);return s;}
}

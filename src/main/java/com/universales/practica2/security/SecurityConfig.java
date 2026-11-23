package com.universales.practica2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFlter;
	
	public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFlter) {
		this.jwtAuthenticationFlter = jwtAuthenticationFlter;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http
			.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
					
					.requestMatchers("/api/noauth/**").permitAll()
					.anyRequest().authenticated()
					
			)
			.exceptionHandling(ex -> ex
				.authenticationEntryPoint((request, response, authException) -> {
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.setContentType("text/plain;charset=UTF-8");
					response.getWriter().write("Acceso no otorgado. Necesita Autenticación");
				})
			)
			.addFilterBefore(jwtAuthenticationFlter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
}

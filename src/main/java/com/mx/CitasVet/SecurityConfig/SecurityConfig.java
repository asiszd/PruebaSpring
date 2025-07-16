package com.mx.CitasVet.SecurityConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mx.CitasVet.Controller.CitaController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	DataSource dataSource;

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.addAllowedOrigin("http://localhost:4200");  // Origen de tu frontend, ajusta según sea necesario
	    configuration.addAllowedMethod("*");  // Permitir todos los métodos HTTP (GET, POST, PUT, DELETE...)
	    configuration.addAllowedHeader("*");  // Permitir todos los encabezados
	    configuration.setAllowCredentials(true);  // Si necesitas permitir credenciales como cookies o headers de autorización

	    // Crear la fuente de configuración CORS
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);  // Aplica esta configuración a todas las rutas

	    return source;
	}

	 @Bean
	 SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		 http
		 	.csrf(csrf -> csrf.disable())
		 	.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		 	.authorizeHttpRequests(auth -> auth
		 			.requestMatchers("/api/citas/**").authenticated()
	                .anyRequest().permitAll())
		 	.httpBasic(Customizer.withDefaults());

	        return http.build();
	    }
	 
	 @Bean
	 PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	 
	 @Bean
	 UserDetailsService userDetailsService() {
		 return new UserDetailsService() {
			 @Override
			 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				 try {
					 Connection conn = dataSource.getConnection();
					 PreparedStatement ps = conn.prepareStatement("SELECT USERNAME, PASSWORD, ROL FROM USERS WHERE USERNAME = ?");
					 ps.setString(1, username.trim().toUpperCase());
					 ResultSet rs = ps.executeQuery();
					 if (rs.next()) {
						 String password = rs.getString("PASSWORD");
						 String rol = rs.getString("ROL");

						 return User.builder()
								 .username(username)
								 .password(password)
								 .roles(rol)
								 .build();
					 } else {
						 throw new UsernameNotFoundException("Usuario no encontrado: " + username);
					 }
				 } catch (SQLException e) {
					 throw new UsernameNotFoundException("Error al acceder a la base de datos", e);
				 }
			 }
		 };
	 }
	 
}

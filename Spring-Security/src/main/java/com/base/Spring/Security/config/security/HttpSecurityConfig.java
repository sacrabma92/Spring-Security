package com.base.Spring.Security.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

   @Autowired
   private AuthenticationProvider authenticationProvider;

   // Cadena para filtro de seguridad
   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
      SecurityFilterChain filterChain = http
              // No utilizara este tipo de token
              .csrf( csrfConfig -> csrfConfig.disable())
              // Aplicación sin estado
              .sessionManagement( sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              // Configuramos la estrategia
              .authenticationProvider( authenticationProvider )
              // Configuración de las rutas
              .authorizeHttpRequests( authReqConfig -> {
                 // Unicas rutas que son publicas y cualquiera puede acceder
                 authReqConfig.requestMatchers(HttpMethod.POST,"/cliente").permitAll();
                 authReqConfig.requestMatchers(HttpMethod.POST,"/auth/authenticate").permitAll();
                 authReqConfig.requestMatchers(HttpMethod.GET,"/auth/validate-token").permitAll();

                 // Todas las demas rutas estaran bloqueadas, necesitaran autenticacion
                 authReqConfig.anyRequest().authenticated();
              })
              .build();
                 return filterChain;
   }
}

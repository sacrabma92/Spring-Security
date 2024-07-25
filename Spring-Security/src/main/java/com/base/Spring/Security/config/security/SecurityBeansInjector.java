package com.base.Spring.Security.config.security;

import com.base.Spring.Security.exception.ObjectNotFoundException;
import com.base.Spring.Security.persistense.repository.security.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansInjector {

   @Autowired
   private UserRepository userRepository;

   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
      return authenticationConfiguration.getAuthenticationManager();
   }

   @Bean
   public AuthenticationProvider authenticationProvider(){
      DaoAuthenticationProvider authenticationStrategy = new DaoAuthenticationProvider();
      authenticationStrategy.setPasswordEncoder( passwordEncoder() );
      authenticationStrategy.setUserDetailsService( userDetailsService() );

      return authenticationStrategy;
   }

   // Metodo para encriptar el password
   @Bean
   public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
   }

   // Devolvera los detalles del usuario que intenta loguearse
   @Bean
   public UserDetailsService userDetailsService(){
      return (username) -> {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found with username " + username));
      };
   }
}

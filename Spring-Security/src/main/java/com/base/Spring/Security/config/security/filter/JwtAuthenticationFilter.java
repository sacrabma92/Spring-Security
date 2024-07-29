package com.base.Spring.Security.config.security.filter;

import com.base.Spring.Security.exception.ObjectNotFoundException;
import com.base.Spring.Security.service.UserService;
import com.base.Spring.Security.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

   @Autowired
   private JwtService jwtService;

   @Autowired
   private UserService userService;

   @Override
   protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain) throws ServletException, IOException {
      // 1. Obtener encabezado HTTP llamado Authorization
      String authorizationHeader = request.getHeader("Authorization"); // Bearer jwt
      // Si esta vacio el jwt
      if(!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
         filterChain.doFilter(request, response);
         return;
      }

      // 2. Obtener token JWT desde el encabezado
      String jwt = authorizationHeader.split(" ")[1];

      // 3. Obtener el Subject/username desde el token, esta accion valida el formato del token
      // firma y fecha de expiración.
      String username = jwtService.extractUsername(jwt);

      // 4. Setear el objeto authentication dentro de security Context Holder
      UserDetails userDetails = userService.findOneByUsername(username)
              .orElseThrow(() -> new ObjectNotFoundException("User not found. Username: " + username));
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              username, null, userDetails.getAuthorities()
      );

      // Me trae detalles de la session como la IP address etc..
      authToken.setDetails( new WebAuthenticationDetails(request) );

      SecurityContextHolder.getContext().setAuthentication(authToken);

      // 5. Ejecutar el registro de filtro
      filterChain.doFilter(request, response);
   }
}

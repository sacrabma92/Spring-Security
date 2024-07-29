package com.base.Spring.Security.service.auth;

import com.base.Spring.Security.dto.Auth.AuthenticationRequest;
import com.base.Spring.Security.dto.Auth.AuthenticationResponse;
import com.base.Spring.Security.dto.RegisteredUser;
import com.base.Spring.Security.dto.SaveUser;
import com.base.Spring.Security.persistense.entity.security.User;
import com.base.Spring.Security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Metodo para registar un nuevo usuario al sistema
    public RegisteredUser registerOneCustomer(SaveUser newUser) {
        // Proceso de guardado del usuario
        User user = userService.registerOneCustomer(newUser);

        // Este es el Dto que le vamos a devolver cuando se guarde con exito.
        RegisteredUser userDto  = new RegisteredUser();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole().getName());

        // Generar jwt
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        userDto.setJwt(jwt);
        // Devolvemos los datos del usuario creado
        return userDto;
    }

    // Generamos el extraClaims, guardamos el name, role, authorities.
    public Map<String, Object> generateExtraClaims(User user){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().getName());
        extraClaims.put("authorities", user.getAuthorities());

        return extraClaims;
    }

    public AuthenticationResponse login(AuthenticationRequest authRequest){
        // Creo el objeto para el login
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            authRequest.getUsername(), authRequest.getPassword()
        );

        // Hago el proceso de login, pasando la autenticación
        authenticationManager.authenticate(authentication);

        // Creo los detalles del objeto
        UserDetails user = userService.findOneByUsername(authRequest.getUsername()).get();

        // Genero el JWT
        String jwt = jwtService.generateToken(user, generateExtraClaims((User) user));

        // Creo la respuesta
        AuthenticationResponse authRsp = new AuthenticationResponse();
        authRsp.setJwt(jwt);
        return authRsp;
    }

    // Metodo que trae los extraclaims y validara el token y que su estructura
    // Sea la correcta para nuestro servidor.
    public boolean validateToken(String jwt){
        try {
            jwtService.extractUsername(jwt);
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}

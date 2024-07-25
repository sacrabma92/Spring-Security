package com.base.Spring.Security.service.auth;

import com.base.Spring.Security.dto.RegisteredUser;
import com.base.Spring.Security.dto.SaveUser;
import com.base.Spring.Security.persistense.entity.security.User;
import com.base.Spring.Security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

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
        String jwt = jwtService.generateToken(user);
        userDto.setJwt(jwt);

        return userDto;
    }
}

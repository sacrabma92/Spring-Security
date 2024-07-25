package com.base.Spring.Security.service.Impl;

import com.base.Spring.Security.dto.SaveUser;
import com.base.Spring.Security.exception.InvalidPasswordException;
import com.base.Spring.Security.exception.ObjectNotFoundException;
import com.base.Spring.Security.persistense.entity.security.Role;
import com.base.Spring.Security.persistense.entity.security.User;
import com.base.Spring.Security.persistense.repository.security.UserRepository;
import com.base.Spring.Security.service.RoleService;
import com.base.Spring.Security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    // Servicio para registrar usuario.
    @Override
    public User registerOneCustomer(SaveUser newUser) {

        // Validamos passwords ingresados.
        validatePassword(newUser);

        User user = new User();
        user.setName(newUser.getName());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setUsername(newUser.getUsername());
        // Le asignamos un role por defecto
        Role defaultRole = roleService.findDefaultRole()
                .orElseThrow(() -> new ObjectNotFoundException("Role not found. Default Role"));
        user.setRole(defaultRole);

        return userRepository.save(user);
    }

    // Validar password ingresados
    private void validatePassword(SaveUser newUser) {
        if(!StringUtils.hasText(newUser.getPassword()) || !StringUtils.hasText(newUser.getRepeatedPassword())){
             throw new InvalidPasswordException("Password don't match");
        }

        if(!newUser.getPassword().equals(newUser.getRepeatedPassword())){
            throw new InvalidPasswordException("Password don't match");
        }
    }

}

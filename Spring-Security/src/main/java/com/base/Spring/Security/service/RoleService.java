package com.base.Spring.Security.service;

import com.base.Spring.Security.persistense.entity.security.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findDefaultRole();

}

package com.base.Spring.Security.persistense.repository.security;

import com.base.Spring.Security.persistense.entity.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String defaultRole);

}

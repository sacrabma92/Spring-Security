package com.base.Spring.Security.persistense.repository.security;

import com.base.Spring.Security.persistense.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}

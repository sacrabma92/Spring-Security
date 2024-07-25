package com.base.Spring.Security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredUser implements Serializable {
    private Long id;

    private String username;

    private String name;

    private String role;

    private String jwt;
}

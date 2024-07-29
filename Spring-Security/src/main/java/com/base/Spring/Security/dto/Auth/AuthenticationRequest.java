package com.base.Spring.Security.dto.Auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthenticationRequest implements Serializable {

   private String username;
   private String password;

}

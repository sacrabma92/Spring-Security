package com.base.Spring.Security.controller;

import com.base.Spring.Security.dto.Auth.AuthenticationRequest;
import com.base.Spring.Security.dto.Auth.AuthenticationResponse;
import com.base.Spring.Security.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

   @Autowired
   private AuthenticationService authenticationService;

   @GetMapping("/validate-token")
   public ResponseEntity<Boolean> validate(@RequestParam String jwt){
      boolean isTokenValid = authenticationService.validateToken(jwt);
      return ResponseEntity.ok(isTokenValid);
   }

   @PostMapping("/authenticate")
   public ResponseEntity<AuthenticationResponse> authenticate(
           @RequestBody @Valid AuthenticationRequest authenticationRequest){
      AuthenticationResponse rsp = authenticationService.login(authenticationRequest);
      return ResponseEntity.ok(rsp);
   }


}

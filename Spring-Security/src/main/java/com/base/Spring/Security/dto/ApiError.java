package com.base.Spring.Security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError implements Serializable {
   private String backendMessage;
   private String message;
   private String url;
   private String method;
   private LocalDateTime timestamp;
}

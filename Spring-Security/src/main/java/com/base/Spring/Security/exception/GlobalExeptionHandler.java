package com.base.Spring.Security.exception;

import com.base.Spring.Security.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExeptionHandler {

   // Muestra error generico
   @ExceptionHandler(Exception.class)
   public ResponseEntity<?> handlerGenericException(Exception exception,
                                                    HttpServletRequest request){
      ApiError apiError = new ApiError();
      apiError.setBackendMessage(exception.getLocalizedMessage());
      apiError.setUrl(request.getRequestURL().toString());
      apiError.setMethod(request.getMethod());
      apiError.setTimestamp(LocalDateTime.now());
      apiError.setMessage("Error interno en el servidor.");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
   }

   // Muestra los errores relacionados con las validaciones de los dto's
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                    HttpServletRequest request){
      ApiError apiError = new ApiError();
      apiError.setBackendMessage(exception.getLocalizedMessage());
      apiError.setUrl(request.getRequestURL().toString());
      apiError.setMethod(request.getMethod());
      apiError.setTimestamp(LocalDateTime.now());
      apiError.setMessage("Error en las validaciones.");

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
   }
}

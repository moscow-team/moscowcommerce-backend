package com.example.moscowcommerce_backend.Shared.Infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.moscowcommerce_backend.Auth.Domain.Exceptions.InvalidTokenException;
import com.example.moscowcommerce_backend.Auth.Domain.Exceptions.UnauthorizedException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de excepciones de validación
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(Result.failure("Surgió un error con los datos proporcionados", errors), HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepciones de autorización no válida
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Result<Map<String, String>>> handleUnauthorizedException(UnauthorizedException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());  // Puedes personalizar el mensaje
        return new ResponseEntity<>(Result.failure("Error al autenticar ",errorResponse), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Result<Map<String, String>>> handleInvalidTokenException(InvalidTokenException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());  // Puedes personalizar el mensaje
        return new ResponseEntity<>(Result.failure("Token inválido", errorResponse), HttpStatus.UNAUTHORIZED);
    }

    // Manejo genérico de excepciones para capturar otros errores no previstos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Map<String, String>>> handleGlobalException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Ha ocurrido un error inesperado.");
        errorResponse.put("details", ex.getMessage());  // Mensaje detallado del error
        return new ResponseEntity<>(Result.failure("Error inesperado", errorResponse), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

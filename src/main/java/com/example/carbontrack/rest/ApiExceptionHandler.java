package com.example.carbontrack.rest;

import com.example.carbontrack.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.example.carbontrack.rest")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> notFound(ResourceNotFoundException exception) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", exception.getMessage())); }

    @ExceptionHandler({DuplicateParticipationException.class, InvalidActivityException.class, InvalidChallengeException.class, ConstraintViolationException.class})
    public ResponseEntity<Map<String, String>> badRequest(RuntimeException exception) { return ResponseEntity.badRequest().body(Map.of("message", exception.getMessage())); }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validation(MethodArgumentNotValidException exception) { return ResponseEntity.badRequest().body(Map.of("message", "Validation failed")); }
}

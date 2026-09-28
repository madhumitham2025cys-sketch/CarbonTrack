package com.example.carbontrack.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class, DuplicateParticipationException.class, InvalidActivityException.class, InvalidChallengeException.class})
    public String handleDomain(RuntimeException exception, Model model) { model.addAttribute("message", exception.getMessage()); return "error"; }
}

package org.example.carrental.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

    @Controller
    public class GlobalExceptionHandler {
        @ExceptionHandler(Exception.class)
        public String handleException(Exception e, Model model) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";  // Ensure this is the correct path to your error view
        }
    }


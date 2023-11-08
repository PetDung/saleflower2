package com.example.demo.controller.controllers;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AccessDenied {
    @GetMapping("/403")
    public String handleAccessDeniedException() {

        return "403";
    }
}

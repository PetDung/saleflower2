package com.example.demo.controller.controllers;

import com.example.demo.repository.RoleRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Login {
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/login")
    public String login(HttpSession session){
        String isStudent = (String) session.getAttribute("MY_SESSION");
        if(isStudent != null){
            return "redirect:/";
        }

        return "login";
    }

    @GetMapping("/sign-up")
    public String s(HttpSession session, Model model){
        String isStudent = (String) session.getAttribute("MY_SESSION");
        if(isStudent != null){
            return "redirect:/";
        }
        model.addAttribute("roles",roleRepository.findAll());
        return "signup";
    }
}

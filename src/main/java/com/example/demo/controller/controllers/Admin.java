package com.example.demo.controller.controllers;

import ch.qos.logback.classic.Logger;
import com.example.demo.model.entity.Plant;
import com.example.demo.model.entity.User;
import com.example.demo.security.CustomUserDetailsService;
import com.example.demo.service.UserserviceI;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class Admin {

    @Autowired
    UserserviceI userserviceI;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;
    @GetMapping("")
    public String index(Model model){
        model.addAttribute("page","chart");
        return "index";
    }

    @GetMapping("/customers")
    public String getCustomers(Model model){
        List<User> customerList =  userserviceI.findAll();
        model.addAttribute("customerList",customerList);
        model.addAttribute("page", "customer");
        return "index";
    }

    @GetMapping("/customer-detail")
    public String getCustomerDetail(Model model, @Param("id") Integer id){
        User user = new User();

        try {
            user = userserviceI.findById(id);
        }catch (Exception e){
            e.toString();
        }
        model.addAttribute("customer",user);
        model.addAttribute("page","customerDetail");
        return "index";
    }
    @PostMapping("/update-customer")
    public String updateUser( Model model,HttpServletRequest request, @RequestParam("id") Integer id,
                             @RequestParam("fullName") String fullName,
                             @RequestParam("email") String email,
                             @RequestParam("phone") String phone) {

        User user = new User();
        user.setId(id);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);

        try {
            userserviceI.updateUser(user);
            request.getSession().setAttribute("MY_SESSION", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SecurityContextHolder.clearContext();
        return "redirect:/admin/customer-detail?id=" + id;
    }

}

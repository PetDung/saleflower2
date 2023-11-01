package com.example.demo.controller.controllers;

import com.example.demo.model.entity.User;
import com.example.demo.service.UserserviceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_Student', 'ROLE_ADMIN')")
public class Admin {

    @Autowired
    UserserviceI userserviceI;

    @GetMapping("")
    public String index(Model model){
        model.addAttribute("page","chart");
        return "index";
    }

    @GetMapping("/customers")
    public String getCustomers(Model model){
        List<User> customerList =  userserviceI.getUsersByRoleName("CUSTOMER");
        System.out.println(customerList.get(0).getFullName());
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

}

package com.example.demo.controller.controllers;

import com.example.demo.model.entity.Plant;
import com.example.demo.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/admin")
@Controller
public class Flower {
    @Autowired
    FlowerService flowerService;

    @GetMapping("/flower")
    public String flower(Model model) {
        List<Plant> plantList = flowerService.getAll();

        model.addAttribute("plantList", plantList);

        model.addAttribute("page", "flower");
        return "index";
    }

}

package com.example.demo.controller.controllers;

import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Plant;
import com.example.demo.model.request.PlantRequest;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/admin")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@Controller
public class Flower {
    @Autowired
    FlowerService flowerService;

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/flower")
    public String flower(Model model) {
        List<Plant> plantList = flowerService.getAll(false);

        model.addAttribute("plantList", plantList);
        model.addAttribute("status", true);
        model.addAttribute("page", "flower");
        return "index";
    }

    @GetMapping("/flower/hiiden")
    public String flowerHidden(Model model) {
        List<Plant> plantList = flowerService.getAll(true);

        model.addAttribute("plantList", plantList);
        model.addAttribute("status", false);
        model.addAttribute("page", "flower");
        return "index";
    }

    @GetMapping("/flower/flower-detail")
    public String flowerDetail(Model model, @RequestParam("id") Integer id) {
        Plant plant = flowerService.findById(id);
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("plant", plant);
        model.addAttribute("page", "flowerDetail");
        return "index";
    }

    @PostMapping("/flower/update")
    public String update(@RequestParam("id") Integer id,
                         @RequestParam("name") String name,
                         @RequestParam("price") double price,
                         @RequestParam("categoryId") Integer categoryId,
                         @RequestParam("description") String description) {
        Plant plant = new Plant();
        plant.setName(name);
        plant.setPrice(price);
        plant.setCId(categoryId);
        plant.setDescription(description);
        plant.setId(id);

        try {
            flowerService.update(plant);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/flower/flower-detail?id=" + id;
    }

    @PostMapping("/flower/create")
    public String create(@RequestParam("name") String name,
                         @RequestParam("price") double price,
                         @RequestParam("categoryId") Integer categoryId,
                         @RequestParam("description") String description) {
        PlantRequest plant = new PlantRequest();
        plant.setName(name);
        plant.setPrice(price);
        plant.setCategoryId(categoryId);
        plant.setDescription(description);
        try {
            flowerService.createPlants(plant);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/flower";
    }

    @GetMapping("/flower/set-hidden")
    public String updateHidden(@RequestParam("id") Integer id) {
        try {
            flowerService.hidden(id, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/flower";
    }

    @GetMapping("/flower/create")
    public String createFlower(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("page", "flowerCreate");
        return "index";
    }

    @GetMapping("/flower/set-appear")
    public String updateAppear(@RequestParam("id") Integer id) {
        try {
            flowerService.hidden(id, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/admin/flower/hiiden";
    }
}

package com.example.demo.controller.restController;

import com.example.demo.model.request.PlantRequest;
import com.example.demo.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/athu")
public class FlowerRest {
    @Autowired
    FlowerService flowerService;

    @PostMapping("/create-flower")
    public ResponseEntity<?> createFlower(@RequestBody PlantRequest plantRequest){
        try {
            flowerService.createPlants(plantRequest);
            return ResponseEntity.ok("succ");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

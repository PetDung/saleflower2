package com.example.demo.service;

import com.example.demo.model.entity.Category;
import com.example.demo.model.entity.Plant;
import com.example.demo.model.request.PlantRequest;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.FlowerRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlowerServiceI implements FlowerService{
    @Autowired
    FlowerRepository flowerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Plant> getAll() {
        return flowerRepository.findAll();
    }

    @Override
    public void createPlants(PlantRequest plantRequest)  {
        Optional<Category> categoryOp = categoryRepository.findById(plantRequest.getCategoryId());
        if(categoryOp.isEmpty()) throw new IllegalStateException("No category");

        Category category = categoryOp.get();

        Plant plant = new Plant();
        plant.setName(plantRequest.getName());
        plant.setPrice(plantRequest.getPrice());
        plant.setCategory(category);

        category.getPlantList().add(plant);

        categoryRepository.save(category);
        flowerRepository.save(plant);

    }
}

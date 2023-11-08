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
    public List<Plant> getAll(Boolean status) {
        return flowerRepository.findAllByStatus(status);
    }

    @Override
    public Plant findById(Integer id) {
        return flowerRepository.findById(id)
                .orElseThrow(() ->{
                    try {
                        throw new NotFoundException("Not found!");
                    } catch (NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public void update(Plant plant) {
        Plant plst = findById(plant.getId());
        Category category = categoryRepository.findById(plant.getCId())
                .orElseThrow(() ->{
                    try {
                        throw new NotFoundException("Not found!");
                    } catch (NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });

        plst.setCategory(category);
        plst.setName(plant.getName());
        plst.setPrice(plant.getPrice());
        plst.setDescription(plant.getDescription());
        flowerRepository.save(plst);
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

    @Override
    public void hidden(Integer id,Boolean hidden) {
        Plant plant = findById(id);
        plant.setStatus(hidden);
        flowerRepository.save(plant);
    }
}

package com.example.demo.service;

import com.example.demo.model.entity.Plant;
import com.example.demo.model.request.PlantRequest;

import java.util.List;

public interface FlowerService {

    List<Plant> getAll(Boolean status);

    Plant findById( Integer id);

    void update(Plant plant);

    void createPlants(PlantRequest plantRequest);

    void hidden(Integer id,Boolean hidden);
}

package com.example.demo.service;

import com.example.demo.model.entity.Plant;
import com.example.demo.model.request.PlantRequest;

import java.util.List;

public interface FlowerService {

    List<Plant> getAll();

    void createPlants(PlantRequest plantRequest);
}

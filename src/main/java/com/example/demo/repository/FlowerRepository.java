package com.example.demo.repository;

import com.example.demo.model.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlowerRepository extends JpaRepository<Plant,Integer> {

    List<Plant> findAllByStatus( Boolean status );
}

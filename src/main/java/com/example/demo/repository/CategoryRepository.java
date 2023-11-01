package com.example.demo.repository;

import com.example.demo.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository  extends JpaRepository<Category,Integer> {

    @Override
    Optional<Category> findById(Integer integer);
}

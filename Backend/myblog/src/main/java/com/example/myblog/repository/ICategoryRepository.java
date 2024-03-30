package com.example.myblog.repository;

import com.example.myblog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Integer> {
    public Category findCategoryByName(String name);
}

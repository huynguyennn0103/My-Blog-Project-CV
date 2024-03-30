package com.example.myblog.service;

import com.example.myblog.entity.Category;
import com.example.myblog.payload.request.categoryRequest.CategoryRequest;
import com.example.myblog.payload.response.category.CategoryResponse;

import java.util.List;

public interface ICategoryService {
    public List<CategoryResponse> findAllCategories();
    public void createCategory(CategoryRequest categoryRequest);

}

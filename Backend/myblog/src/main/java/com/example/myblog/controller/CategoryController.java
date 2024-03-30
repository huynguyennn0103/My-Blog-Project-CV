package com.example.myblog.controller;

import com.example.myblog.payload.request.categoryRequest.CategoryRequest;
import com.example.myblog.payload.response.base.BaseResponse;
import com.example.myblog.payload.response.category.CategoryResponse;
import com.example.myblog.service.ICategoryService;
import com.example.myblog.service.imp.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("categories")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;
    @GetMapping("")
    public ResponseEntity<?> getAllCategories(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(iCategoryService.findAllCategories());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PostMapping("")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        BaseResponse baseResponse = new BaseResponse();
        iCategoryService.createCategory(categoryRequest);
        baseResponse.setData(true);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}

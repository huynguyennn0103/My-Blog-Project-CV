package com.example.myblog.service.imp;

import com.example.myblog.entity.Category;
import com.example.myblog.payload.request.categoryRequest.CategoryRequest;
import com.example.myblog.payload.response.category.CategoryResponse;
import com.example.myblog.repository.ICategoryRepository;
import com.example.myblog.service.ICategoryService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository iCategoryRepository;
    @Autowired
    private RedisTemplate redisTemplate;

    private Gson gson = new Gson();

    @Override
    public List<CategoryResponse> findAllCategories() {

        List<Category> lists = new ArrayList<>();
        boolean storeRedis = false;
        if(redisTemplate.hasKey("listCategories")){
            String dataCategories = (String) redisTemplate.opsForValue().get("listCategories");
            Type listType = new TypeToken<ArrayList<Category>>(){}.getType();
            lists = new Gson().fromJson(dataCategories, listType);
        } else {
            System.out.println("It still run the code in findAll");
            lists = iCategoryRepository.findAll();
            storeRedis = true;
        }


        List<CategoryResponse> categoryResponseList = new ArrayList<>();
        if(lists.size() > 0){
            for(Category data: lists){
                CategoryResponse category = new CategoryResponse();
                category.setId(data.getId());
                category.setName(data.getName());
                categoryResponseList.add(category);
            }
        }
        if(storeRedis){
            String data = gson.toJson(categoryResponseList);
            redisTemplate.opsForValue().set("listCategories", data);
            redisTemplate.expire("listCategories", Duration.ofHours(1));
        }

        return categoryResponseList;
    }

    @Override
    public void createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        if(redisTemplate.hasKey("listCategories")){
            redisTemplate.delete("listCategories");
        }
        iCategoryRepository.save(category);
    }
}

package com.example.myblog.payload.response.post;

import com.example.myblog.entity.Category;
import com.example.myblog.payload.response.category.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostResponse {
    private Integer id;
    private String title;
    private String description;
    private String photo;
    private Date createdAt;
    private PostUserResponse user;
    private List<CategoryResponse> categories;
}

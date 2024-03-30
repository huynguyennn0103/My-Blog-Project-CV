package com.example.myblog.payload.request.postRequest;

import com.example.myblog.payload.response.category.CategoryResponse;
import com.example.myblog.payload.response.post.PostUserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostRequest {
    @NotNull(message = "Field 'title' of post is required")
    @NotBlank(message = "Field 'title' of post can not be empty")
    private String title;

    @NotNull(message = "Field 'description' of post is required")
    @NotBlank(message = "Field 'description' of post can not be empty")
    private String description;


    @NotNull(message = "Field 'photo' of post is required")
    @NotBlank(message = "Field 'photo' of post can not be empty")
    private String photo;

    @NotNull(message = "Field 'author_id' of post is required")
    private int author_id;

    @NotNull(message = "Field 'categories' of post is required")
    private String categories;
}

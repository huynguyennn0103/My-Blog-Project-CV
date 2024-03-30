package com.example.myblog.payload.request.categoryRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryRequest {
    @NotNull(message = "Field 'name' of category is required")
    private String name;
}

package com.example.myblog.payload.response.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryResponse {
    private Integer id;
    private String name;
}

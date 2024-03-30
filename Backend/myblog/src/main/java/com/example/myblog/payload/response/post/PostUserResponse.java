package com.example.myblog.payload.response.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostUserResponse {
    private Integer id;
    private String username;
}

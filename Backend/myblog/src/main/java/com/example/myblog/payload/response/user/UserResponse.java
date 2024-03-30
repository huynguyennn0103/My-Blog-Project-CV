package com.example.myblog.payload.response.user;

import lombok.*;

import javax.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String profilePic;
}

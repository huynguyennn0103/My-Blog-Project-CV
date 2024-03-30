package com.example.myblog.payload.request.userRequest;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Data
@Setter
public class UserRequest {
    private Integer id;

    @NotNull(message = "Field 'username' of user is required")
    @NotBlank(message = "Field 'username' of user can not be empty")
    private String username;

    @NotNull(message = "Field 'email' of user is required")
    @NotBlank(message = "Field 'email' of user can not be empty")
    private String email;

    @NotNull(message = "Field 'password' of user is required")
    @NotBlank(message = "Field 'password' of user can not be empty")
    private String password;

    @NotNull(message = "Field 'profilePic' of user is required")
    private String profilePic;
}

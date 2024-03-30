package com.example.myblog.service;

import com.example.myblog.entity.User;
import com.example.myblog.payload.request.userRequest.UserRequest;
import com.example.myblog.payload.response.user.UserResponse;

import java.util.List;

public interface IUserService {
    public List<UserResponse> findAllUsers();
    public UserResponse findUserById(int id);
    public UserResponse findUserByEmail(String email);

    public boolean registerUser(UserRequest userRequest);

    public UserResponse updateUserById(int id, UserRequest updateUser);
    public boolean updateAvaById(String filename,int id);
    public boolean deleteUser(int id, UserRequest userRequest);

}

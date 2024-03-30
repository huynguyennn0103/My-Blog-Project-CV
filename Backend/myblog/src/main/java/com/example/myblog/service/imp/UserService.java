package com.example.myblog.service.imp;

import com.example.myblog.entity.User;
import com.example.myblog.payload.request.userRequest.UserRequest;
import com.example.myblog.payload.response.user.UserResponse;
import com.example.myblog.repository.IUserRepository;
import com.example.myblog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public List<UserResponse> findAllUsers() {
        List<UserResponse> userResponseList = new ArrayList<>();
        List<User> userList = iUserRepository.findAll();
        if(userList.size() > 0){
            for(User data: userList){
                UserResponse userResponse = new UserResponse();
                userResponse.setId(data.getId());
                userResponse.setPassword(data.getPassword());
                userResponse.setUsername(data.getUsername());
                userResponse.setProfilePic(data.getProfilePic());
                userResponse.setEmail(data.getEmail());
                userResponseList.add(userResponse);
            }
        }
        return userResponseList;
    }

    @Override
    public UserResponse findUserById(int id) {
        User user = iUserRepository.findById(id).orElse(null);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());
        userResponse.setPassword(user.getPassword());
        userResponse.setProfilePic(user.getProfilePic());
        return userResponse;
    }

    @Override
    public UserResponse findUserByEmail(String email) {
        User user = iUserRepository.findByEmail(email);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setUsername(user.getUsername());
        userResponse.setPassword(user.getPassword());
        userResponse.setProfilePic(user.getProfilePic());
        return userResponse;
    }

    @Override
    public boolean registerUser(UserRequest userRequest) {
        User checkReplicas = iUserRepository.findByEmail(userRequest.getEmail());
        if(checkReplicas == null){
            User newUser = new User();
            newUser.setEmail(userRequest.getEmail());
            newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            newUser.setProfilePic(userRequest.getProfilePic());
            newUser.setUsername(userRequest.getUsername());
            iUserRepository.save(newUser);
            return false;
        }
        return true;
    }

    @Override
    public UserResponse updateUserById(int id, UserRequest updateUser) {
        User user = iUserRepository.findById(id).orElse(null);
        UserResponse userResponse = new UserResponse();

        if(user != null){
            user.setUsername(updateUser.getUsername());
            user.setEmail(updateUser.getEmail());
            user.setPassword(updateUser.getPassword());

            userResponse.setId(user.getId());
            userResponse.setEmail(user.getEmail());
            userResponse.setUsername(user.getUsername());
            userResponse.setPassword(user.getPassword());
            userResponse.setProfilePic(user.getProfilePic());
        } else{
            return null;
        }

        try{
            iUserRepository.save(user);
        } catch (Exception e){
            return null;
        }
        return userResponse;
    }

    @Override
    public boolean updateAvaById(String filename, int id) {
        User user = iUserRepository.findById(id).orElse(null);
        if(user == null) return false;
        user.setProfilePic(filename);
        iUserRepository.save(user);
        return true;
    }

    @Override
    public boolean deleteUser(int id, UserRequest userRequest) {
        User user = iUserRepository.findById(id).orElse(null);
        if(user == null){
            return false;
        }
        iUserRepository.deleteById(id);
        return true;
    }
}

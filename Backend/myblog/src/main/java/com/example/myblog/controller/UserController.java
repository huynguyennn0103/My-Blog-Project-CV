package com.example.myblog.controller;

import com.example.myblog.payload.request.userRequest.UserRequest;
import com.example.myblog.payload.response.base.BaseResponse;
import com.example.myblog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("")
    public ResponseEntity<?> getAllUsers(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(iUserService.findAllUsers());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){
        Integer id_num = Integer.parseInt(id);
        BaseResponse baseResponse  = new BaseResponse();
        baseResponse.setData(iUserService.findUserById(id_num));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<?> getUserByEmail(@RequestParam String email){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(iUserService.findUserByEmail(email));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest){
        BaseResponse baseResponse = new BaseResponse();
        boolean isEmailExisted = iUserService.registerUser(userRequest);
        if(isEmailExisted){
            baseResponse.setStatusCode(500);
           baseResponse.setData("");
           baseResponse.setMessage("Email is existed, choose another email");
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable String id,@Valid @RequestBody UserRequest userRequest){
        BaseResponse baseResponse = new BaseResponse();
        if(userRequest.getId() == Integer.parseInt(id)){
            baseResponse.setData(iUserService.updateUserById(Integer.parseInt(id),userRequest ));
        }
        else{
            baseResponse.setData("");
            baseResponse.setMessage("You can update only your account");
            baseResponse.setStatusCode(500);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id, @RequestBody UserRequest userRequest){
        BaseResponse baseResponse = new BaseResponse();
        if(userRequest.getId() == Integer.parseInt(id)){
            boolean isSuccess = iUserService.deleteUser(Integer.parseInt(id),userRequest);
            if(!isSuccess){
                baseResponse.setData("");
                baseResponse.setMessage("You need to provide correct user_id");
                baseResponse.setStatusCode(500);
            }
        }
        else{
            baseResponse.setData("");
            baseResponse.setMessage("You can update only your account");
            baseResponse.setStatusCode(500);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadAva(@RequestParam("file") MultipartFile multipartFile, @RequestParam("id") int id) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String uploadDir = "./public/avas/" + id;
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new IOException("Could not save upload file: " + fileName);
        }
        BaseResponse baseResponse = new BaseResponse();
        String path = fileName;
        boolean isSuccess = iUserService.updateAvaById(fileName, id);
        if(isSuccess){
            baseResponse.setData(path);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setStatusCode(500);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}

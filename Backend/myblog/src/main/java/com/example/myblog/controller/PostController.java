package com.example.myblog.controller;

import com.example.myblog.payload.request.postRequest.PostRequest;
import com.example.myblog.payload.response.base.BaseResponse;
import com.example.myblog.payload.response.post.PostResponse;
import com.example.myblog.service.IPostService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {
    @Autowired
    private IPostService iPostService;


    @GetMapping("")
    public ResponseEntity<?> findAllPosts(@RequestParam(required = false) String user, @RequestParam(required = false) String cat, @RequestParam int page, @RequestParam(required = false) String title){
        BaseResponse baseResponse = new BaseResponse();
        if(user == null && cat == null && title == null){
            baseResponse.setData(iPostService.getPaginatedPosts(page, 5));
        }
        else if(user != null) {
            baseResponse.setData(iPostService.findPostsByUsername(user, page, 5));
        }
        else if(cat != null){
            baseResponse.setData(iPostService.findPostByCategoriesContains(cat, page, 5));
        }
        else if (title != null){
            baseResponse.setData(iPostService.findPostByTitleContains(title, page, 5));
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findPostById(@PathVariable String id){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(iPostService.findPostById(Integer.parseInt(id)));
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createPost( @Valid @RequestBody PostRequest postRequest){
        BaseResponse baseResponse =new BaseResponse();
        PostResponse postResponse =  iPostService.createPost(postRequest);
        baseResponse.setData(postResponse);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @DeleteMapping("/upload")
    public ResponseEntity<?> deletePostImg(@RequestParam("author_id") int author_id, @RequestParam("img_name") String img_name){
        String filePath = "./public/posts/" + author_id + "/" + img_name;
        File file = new File(filePath);
        BaseResponse baseResponse = new BaseResponse();
        boolean isDeleteSuccess = false;
        if(file.exists()){
            isDeleteSuccess = file.delete();
        }
        if(!isDeleteSuccess){
            baseResponse.setData(false);
            baseResponse.setMessage("Delete image of post failed");
            baseResponse.setStatusCode(500);
        } else {
            baseResponse.setData(true);
            baseResponse.setMessage("Delete image of post successfuly");
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadPost(@RequestParam("file") MultipartFile multipartFile, @RequestParam("author_id") int author_id) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        String fileCode = RandomStringUtils.randomAlphanumeric(10);
        String uploadDir = "./public/posts/" + author_id;
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            throw new IOException("Could not save upload file: " + fileName);
        }
        BaseResponse baseResponse = new BaseResponse();
        String path = fileCode + "-" + fileName;
        baseResponse.setData(path);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(@PathVariable String id,@Valid @RequestBody PostRequest postRequest){
        BaseResponse baseResponse = new BaseResponse();
        PostResponse response = iPostService.updatePost(Integer.parseInt(id), postRequest);
        if(response == null){
            baseResponse.setMessage("You can update only your post");
            baseResponse.setStatusCode(500);
            baseResponse.setData("");
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable String id, @RequestBody PostRequest postRequest){
        BaseResponse baseResponse = new BaseResponse();
        boolean isSuccess = iPostService.deletePost(Integer.parseInt(id), postRequest);
        if(isSuccess){
            baseResponse.setData(true);
        }
        else {
            baseResponse.setStatusCode(500);
            baseResponse.setMessage("You can only delete your post");
            baseResponse.setData(false);
        }
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}

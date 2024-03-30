package com.example.myblog.service;

import com.example.myblog.entity.Category;
import com.example.myblog.entity.Post;
import com.example.myblog.payload.request.postRequest.PostRequest;
import com.example.myblog.payload.response.base.PaginationResponse;
import com.example.myblog.payload.response.post.PostResponse;

import java.util.List;

public interface IPostService {
    public PaginationResponse getPaginatedPosts(int page, int size);
    public PostResponse findPostById(int id);
    public PaginationResponse findPostsByUsername(String username, int page, int size);
    public PaginationResponse findPostByCategoriesContains(String cat, int page, int size);
    public PaginationResponse findPostByTitleContains(String title, int page, int size);
    public PostResponse createPost(PostRequest postRequest);

    public PostResponse updatePost(int id,PostRequest postRequest);
    public boolean deletePost(int id, PostRequest postRequest);

}

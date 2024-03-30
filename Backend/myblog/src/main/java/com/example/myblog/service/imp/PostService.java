package com.example.myblog.service.imp;

import com.example.myblog.entity.Category;
import com.example.myblog.entity.Post;
import com.example.myblog.entity.User;
import com.example.myblog.payload.request.postRequest.PostRequest;
import com.example.myblog.payload.response.base.PaginationResponse;
import com.example.myblog.payload.response.category.CategoryResponse;
import com.example.myblog.payload.response.post.PostResponse;
import com.example.myblog.payload.response.post.PostUserResponse;
import com.example.myblog.repository.ICategoryRepository;
import com.example.myblog.repository.IPostRepository;
import com.example.myblog.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService implements IPostService {
    @Autowired
    private IPostRepository iPostRepository;
    @Autowired
    private ICategoryRepository iCategoryRepository;
    @Override
    public PaginationResponse getPaginatedPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Post> postPage = iPostRepository.findAll(pageable);
        List<Post> list = postPage.getContent();
        List<PostResponse> postResponseList = new ArrayList<>();
        if(list != null){
            for(Post data: list){
                PostResponse postResponse = new PostResponse();
                postResponse.setId(data.getId());
                postResponse.setTitle(data.getTitle());
                postResponse.setDescription(data.getDescription());
                postResponse.setPhoto(data.getPhoto());
                postResponse.setCreatedAt(data.getCreatedAt());

                PostUserResponse postUserResponse = new PostUserResponse();
                if(data.getUser() != null){
                    postUserResponse.setId(data.getUser().getId());
                    postUserResponse.setUsername(data.getUser().getUsername());
                }
                postResponse.setUser(postUserResponse);

                if(data.getCategories() != null){
                    List<CategoryResponse> categoryResponseList = new ArrayList<>();
                    for(Category c : data.getCategories()){
                        CategoryResponse categoryResponse = new CategoryResponse();
                        categoryResponse.setId(c.getId());
                        categoryResponse.setName(c.getName());
                        categoryResponseList.add(categoryResponse);
                    }
                    postResponse.setCategories(categoryResponseList);
                }
                postResponseList.add(postResponse);
            }
        }
        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(postResponseList);
        paginationResponse.setPageCount(postPage.getTotalPages());
        return paginationResponse;
    }

    @Override
    public PostResponse findPostById(int id) {
        Post post = iPostRepository.findById(id).orElse(null);
        PostResponse postResponse = new PostResponse();
        if(post != null){
            postResponse.setId(post.getId());
            postResponse.setDescription(post.getDescription());
            postResponse.setPhoto(post.getPhoto());
            postResponse.setTitle(post.getTitle());
            postResponse.setCreatedAt(post.getCreatedAt());

            PostUserResponse postUserResponse = new PostUserResponse();
            if(post.getUser() != null){
                postUserResponse.setId(post.getUser().getId());
                postUserResponse.setUsername(post.getUser().getUsername());
            }
            postResponse.setUser(postUserResponse);

            List<CategoryResponse> categoryResponseList = new ArrayList<>();
            if(post.getCategories() != null){
                for(Category c : post.getCategories()){
                    CategoryResponse categoryResponse = new CategoryResponse();
                    categoryResponse.setId(c.getId());
                    categoryResponse.setName(c.getName());
                    categoryResponseList.add(categoryResponse);
                }
            }
            postResponse.setCategories(categoryResponseList);
        }
        return postResponse;
    }

    @Override
    public PaginationResponse findPostsByUsername(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Post> postPage = iPostRepository.findPostsByUserUsername(username, pageable);
        List<Post> list = postPage.getContent();

        List<PostResponse> postResponseList = new ArrayList<>();
        if(list != null){
            for(Post data: list){
                PostResponse postResponse = new PostResponse();
                postResponse.setId(data.getId());
                postResponse.setTitle(data.getTitle());
                postResponse.setDescription(data.getDescription());
                postResponse.setPhoto(data.getPhoto());
                postResponse.setCreatedAt(data.getCreatedAt());

                PostUserResponse postUserResponse = new PostUserResponse();
                if(data.getUser() != null){
                    postUserResponse.setId(data.getUser().getId());
                    postUserResponse.setUsername(data.getUser().getUsername());
                }
                postResponse.setUser(postUserResponse);

                if(data.getCategories() != null){
                    List<CategoryResponse> categoryResponseList = new ArrayList<>();
                    for(Category c : data.getCategories()){
                        CategoryResponse categoryResponse = new CategoryResponse();
                        categoryResponse.setId(c.getId());
                        categoryResponse.setName(c.getName());
                        categoryResponseList.add(categoryResponse);
                    }
                    postResponse.setCategories(categoryResponseList);
                }
                postResponseList.add(postResponse);
            }
        }
        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(postResponseList);
        paginationResponse.setPageCount(postPage.getTotalPages());
        return paginationResponse;
    }

    @Override
    public PaginationResponse findPostByCategoriesContains(String cat, int page, int size) {
        Category category = iCategoryRepository.findCategoryByName(cat);
        if(category == null) return null;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Post> postPage = iPostRepository.findPostsByCategoriesContains(category, pageable);
        List<Post> list = postPage.getContent();
        List<PostResponse> postResponseList = new ArrayList<>();
        if(list != null){
            for(Post data: list){
                PostResponse postResponse = new PostResponse();
                postResponse.setId(data.getId());
                postResponse.setTitle(data.getTitle());
                postResponse.setDescription(data.getDescription());
                postResponse.setPhoto(data.getPhoto());
                postResponse.setCreatedAt(data.getCreatedAt());

                PostUserResponse postUserResponse = new PostUserResponse();
                if(data.getUser() != null){
                    postUserResponse.setId(data.getUser().getId());
                    postUserResponse.setUsername(data.getUser().getUsername());
                }
                postResponse.setUser(postUserResponse);

                if(data.getCategories() != null){
                    List<CategoryResponse> categoryResponseList = new ArrayList<>();
                    for(Category c : data.getCategories()){
                        CategoryResponse categoryResponse = new CategoryResponse();
                        categoryResponse.setId(c.getId());
                        categoryResponse.setName(c.getName());
                        categoryResponseList.add(categoryResponse);
                    }
                    postResponse.setCategories(categoryResponseList);
                }
                postResponseList.add(postResponse);
            }
        }
        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(postResponseList);
        paginationResponse.setPageCount(postPage.getTotalPages());
        return paginationResponse;
    }

    @Override
    public PaginationResponse findPostByTitleContains(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<Post> postPage = iPostRepository.findPostsByTitleContains(title.toLowerCase(), pageable);
        List<Post> list = postPage.getContent();


        List<PostResponse> postResponseList = new ArrayList<>();
        if(list != null){
            for(Post data: list){
                PostResponse postResponse = new PostResponse();
                postResponse.setId(data.getId());
                postResponse.setTitle(data.getTitle());
                postResponse.setDescription(data.getDescription());
                postResponse.setPhoto(data.getPhoto());
                postResponse.setCreatedAt(data.getCreatedAt());

                PostUserResponse postUserResponse = new PostUserResponse();
                if(data.getUser() != null){
                    postUserResponse.setId(data.getUser().getId());
                    postUserResponse.setUsername(data.getUser().getUsername());
                }
                postResponse.setUser(postUserResponse);

                if(data.getCategories() != null){
                    List<CategoryResponse> categoryResponseList = new ArrayList<>();
                    for(Category c : data.getCategories()){
                        CategoryResponse categoryResponse = new CategoryResponse();
                        categoryResponse.setId(c.getId());
                        categoryResponse.setName(c.getName());
                        categoryResponseList.add(categoryResponse);
                    }
                    postResponse.setCategories(categoryResponseList);
                }
                postResponseList.add(postResponse);
            }
        }
        PaginationResponse paginationResponse = new PaginationResponse();
        paginationResponse.setContent(postResponseList);
        paginationResponse.setPageCount(postPage.getTotalPages());
        return paginationResponse;
    }

    @Override
    public PostResponse createPost(PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setPhoto(postRequest.getPhoto());

        User user = new User();
        user.setId(postRequest.getAuthor_id());
        post.setUser(user);
        post.setCreatedAt(new Date());

        Set<Category> categories = new HashSet<>();
        String input = postRequest.getCategories().replace("[", "").replace("]", "").replace("'","");
        String[] elements = input.split(",");
        if(elements.length != 0){
            for(String data: elements){
                Category category = new Category();
                category.setId(Integer.parseInt(data));
                categories.add(category);
            }
        }
        post.setCategories(categories);

        Post savePost = iPostRepository.save(post);
        PostResponse postResponse = new PostResponse();
        postResponse.setId(savePost.getId());
        return postResponse;
    }

    @Override
    public PostResponse updatePost(int id, PostRequest postRequest) {
        Post post = iPostRepository.findById(id).orElse(null);
        if(post.getUser().getId() != postRequest.getAuthor_id()){
            return null;
        }
        post.setTitle(postRequest.getTitle());
        post.setDescription(postRequest.getDescription());
        post.setCreatedAt(new Date());
//        post.setPhoto(postRequest.getPhoto());

        Set<Category> categories = new HashSet<>();
        String input = postRequest.getCategories().replace("[", "").replace("]", "").replace("'","");
        String[] elements = input.split(",");
        if(elements.length != 0){
            for(String data: elements){
                Category category = new Category();
                category.setId(Integer.parseInt(data));
                categories.add(category);
            }
        }
        post.setCategories(categories);
        iPostRepository.save(post);

        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setDescription(post.getDescription());
        postResponse.setPhoto(post.getPhoto());
        postResponse.setCreatedAt(post.getCreatedAt());

        PostUserResponse postUserResponse = new PostUserResponse();
        if(post.getUser() != null){
            postUserResponse.setId(post.getUser().getId());
            postUserResponse.setUsername(post.getUser().getUsername());
        }
        postResponse.setUser(postUserResponse);

        if(post.getCategories() != null){
            List<CategoryResponse> categoryResponseList = new ArrayList<>();
            for(Category c : post.getCategories()){
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setId(c.getId());
                categoryResponse.setName(c.getName());
                categoryResponseList.add(categoryResponse);
            }
            postResponse.setCategories(categoryResponseList);
        }
        return postResponse;
    }

    @Override
    public boolean deletePost(int id, PostRequest postRequest) {
        Post post = iPostRepository.findById(id).orElse(null);
        if(post.getUser().getId() != postRequest.getAuthor_id()){
            return false;
        }
        iPostRepository.deleteById(id);
        return true;
    }
}

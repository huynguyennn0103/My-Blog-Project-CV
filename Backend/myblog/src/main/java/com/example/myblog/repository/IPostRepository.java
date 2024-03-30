package com.example.myblog.repository;

import com.example.myblog.entity.Category;
import com.example.myblog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Integer> {
    public Page<Post> findPostsByUserUsername(String username, Pageable pageable);
    public Page<Post> findPostsByCategoriesContains(Category category, Pageable pageable);
    public Page<Post> findPostsByTitleContains(String title, Pageable pageable);

}

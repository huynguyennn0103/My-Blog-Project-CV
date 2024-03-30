package com.example.myblog.repository;

import com.example.myblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
}

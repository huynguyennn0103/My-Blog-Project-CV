package com.example.myblog.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<PostCategory> postCategorySet = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "posts_categories",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id"))
    private Set<Post> posts = new LinkedHashSet<>();
}

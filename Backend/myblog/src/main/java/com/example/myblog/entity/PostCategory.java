package com.example.myblog.entity;

import com.example.myblog.entity.ids.PostCategoryId;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "posts_categories")
public class PostCategory {
    @EmbeddedId
    private PostCategoryId id;

    @MapsId("postId")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "post_id")
    private Post post;

    @MapsId("categoryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "category_id")
    private Category category;
}

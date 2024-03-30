package com.example.myblog.entity.ids;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PostCategoryId implements Serializable {
    private static final long serialVersionUID = 5847987285228239424L;
    @Column(name = "post_id")
    private Integer postId;

    @Column (name = "category_id")
    private Integer categoryId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PostCategoryId entity = (PostCategoryId) o;
        return Objects.equals(this.postId, entity.postId) &&
                Objects.equals(this.categoryId, entity.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postId, categoryId);
    }
}

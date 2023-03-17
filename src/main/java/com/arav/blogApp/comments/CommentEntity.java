package com.arav.blogApp.comments;

import com.arav.blogApp.blogs.BlogEntity;
import com.arav.blogApp.common.BaseEntity;
import com.arav.blogApp.users.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="comments")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity extends BaseEntity {

    @Column(nullable = false,length = 100)
    private String title;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    private BlogEntity blog;
}

package com.arav.blogApp.blogs;

import com.arav.blogApp.comments.CommentEntity;
import com.arav.blogApp.common.BaseEntity;
import com.arav.blogApp.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name="blogs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogEntity extends BaseEntity {

    @Column(nullable = false,length = 100)
    String title;

    @Column(nullable = false,length = 100,unique = true)
    String slug;

    @Column(nullable = false,length = 150)
    String subtitle;

    @Column(columnDefinition = "TEXT",nullable = false)
    String body;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    UserEntity author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "blog_likers",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<UserEntity> likers;

    @OneToMany(mappedBy = "blog",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<CommentEntity> comments;
}

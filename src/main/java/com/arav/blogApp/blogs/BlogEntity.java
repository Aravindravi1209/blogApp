package com.arav.blogApp.blogs;

import com.arav.blogApp.comments.CommentEntity;
import com.arav.blogApp.common.BaseEntity;
import com.arav.blogApp.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity(name="blogs")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BlogEntity extends BaseEntity {

    @Column(nullable = false,length = 100)
    private String title;

    @Column(nullable = false,length = 100,unique = true)
    private String slug;

    @Column(nullable = false,length = 150)
    private String subtitle;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String body;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "blog_likers",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<UserEntity> likers;

    @OneToMany(mappedBy = "blog",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CommentEntity> comments;

    private List<String> tags;
}

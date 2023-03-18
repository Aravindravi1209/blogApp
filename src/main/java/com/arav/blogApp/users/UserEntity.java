package com.arav.blogApp.users;

import com.arav.blogApp.blogs.BlogEntity;
import com.arav.blogApp.common.BaseEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity(name="users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Nullable
    private String bio;
    @Nullable
    private String image;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BlogEntity> authoredBlogs;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "likers")
    private Set<BlogEntity> likedBlogs;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )

    private Set<UserEntity> followers;

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "followers")
    private Set<UserEntity> following;
}

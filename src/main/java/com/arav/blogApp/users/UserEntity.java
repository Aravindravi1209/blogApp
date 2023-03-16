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
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    @Column(nullable = false, unique = true)
    String username;
    @Column(nullable = false)
    @Setter
    String password;
    @Column(nullable = false, unique = true)
    String email;
    @Nullable
    @Setter
    String bio;
    @Nullable
    @Setter
    String image;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<BlogEntity> authoredBlogs;

    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "likers")
    Set<BlogEntity> likedBlogs;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "following_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    Set<UserEntity> followers;

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "followers")
    Set<UserEntity> following;


}

package com.arav.blogApp.common;

import jakarta.persistence.*;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id",nullable = false)
    Long id;

    @Setter @CreationTimestamp
    Date createdAt;

    @Setter @UpdateTimestamp
    Date updatedAt;

}

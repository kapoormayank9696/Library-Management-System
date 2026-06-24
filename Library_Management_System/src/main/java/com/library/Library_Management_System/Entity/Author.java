package com.library.Library_Management_System.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Author")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private int authorId;

    @Column(name = "author_name",nullable = false,unique = true)
    private String authorName;
}

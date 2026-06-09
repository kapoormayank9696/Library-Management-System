package com.library.Library_Management_System.Repository;

import com.library.Library_Management_System.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {}

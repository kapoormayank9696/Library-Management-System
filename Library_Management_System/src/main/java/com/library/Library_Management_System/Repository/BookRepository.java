package com.library.Library_Management_System.Repository;

import com.library.Library_Management_System.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

    boolean existsByAuthor_AuthorId(Integer authorId);
}
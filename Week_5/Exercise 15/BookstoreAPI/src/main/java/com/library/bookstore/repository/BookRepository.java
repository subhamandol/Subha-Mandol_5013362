package com.library.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.bookstore.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Find books by title
    List<Book> findByTitle(String title);
    
    // Find books by author
    List<Book> findByAuthor(String author);
}

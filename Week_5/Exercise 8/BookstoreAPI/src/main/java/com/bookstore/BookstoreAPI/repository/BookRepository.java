package com.bookstore.BookstoreAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.BookstoreAPI.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}

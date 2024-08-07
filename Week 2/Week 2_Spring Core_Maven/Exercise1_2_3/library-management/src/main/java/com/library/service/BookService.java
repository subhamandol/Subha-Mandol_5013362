package com.library.service;

import com.library.repository.BookRepository;

public class BookService {
    private BookRepository bookRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void manageBooks() {
        // Logic for managing books
        System.out.println("Managing books...");

        // Use bookRepository to save a book
        bookRepository.saveBook();
    }
}

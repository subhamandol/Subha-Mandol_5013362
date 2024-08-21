package com.library;

import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;

    // Constructor Injection (recommended)
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void doService() {
        // Use the bookRepository to perform some operation
        bookRepository.doRepositoryWork();
        System.out.println("Service method executed");
    }
}

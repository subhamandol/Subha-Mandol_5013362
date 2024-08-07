package com.lib;

import java.util.List;

public class BookService {

    private BookRepository bookRepository;

    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Method to get all books
    public List<String> getAllBooks() {
        return bookRepository.findAllBooks();
    }

    // Method to add a book
    public void addBook(String book) {
        bookRepository.saveBook(book);
    }

    // Method to display all books
    public void displayBooks() {
        List<String> books = getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            System.out.println("Books in the library:");
            for (String book : books) {
                System.out.println(book);
            }
        }
    }
}

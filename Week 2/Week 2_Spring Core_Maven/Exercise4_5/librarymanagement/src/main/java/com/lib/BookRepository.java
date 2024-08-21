package com.lib;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {

    private List<String> books = new ArrayList<>();

    // Method to retrieve all books
    public List<String> findAllBooks() {
        return new ArrayList<>(books); // Return a copy to prevent external modifications
    }

    // Method to save a book
    public void saveBook(String book) {
        books.add(book);
    }
}

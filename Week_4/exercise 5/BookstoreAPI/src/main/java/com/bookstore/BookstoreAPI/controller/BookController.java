package com.bookstore.BookstoreAPI.controller;

import com.bookstore.BookstoreAPI.model.Book;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> bookList = new ArrayList<>();

    // Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BookListHeader");

        return ResponseEntity.ok()
                .headers(headers)
                .body(bookList);
    }

    // Get a book by ID with custom status
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookList.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();

        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Custom-Header", "BookNotFound")
                    .body(null);
        }
    }

    // Add a new book with custom status and headers
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        bookList.add(book);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BookAddedSuccessfully");

        return new ResponseEntity<>(book, headers, HttpStatus.CREATED);
    }

    // Update an existing book with custom status
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Optional<Book> book = bookList.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();

        if (book.isPresent()) {
            book.get().setTitle(updatedBook.getTitle());
            book.get().setAuthor(updatedBook.getAuthor());
            book.get().setPrice(updatedBook.getPrice());
            book.get().setIsbn(updatedBook.getIsbn());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "BookUpdatedSuccessfully");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(book.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Custom-Header", "BookNotFound")
                    .body(null);
        }
    }

    // Delete a book with custom status
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = bookList.removeIf(book -> book.getId().equals(id));

        if (removed) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "BookDeletedSuccessfully");

            return ResponseEntity.noContent()
                    .headers(headers)
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Custom-Header", "BookNotFound")
                    .build();
        }
    }
}

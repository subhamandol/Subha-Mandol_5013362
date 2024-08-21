/*package com.bookstore.BookstoreAPI.controller;

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
*/

// Updated BookController.java
package com.bookstore.BookstoreAPI.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.BookstoreAPI.dto.BookDTO;
import com.bookstore.BookstoreAPI.model.Book;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> bookList = new ArrayList<>();
    private final ModelMapper modelMapper;

    public BookController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Get all books
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BookListHeader");

        List<BookDTO> bookDTOList = bookList.stream()
                .map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .headers(headers)
                .body(bookDTOList);
    }

    // Get a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookList.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();

        if (book.isPresent()) {
            BookDTO bookDTO = modelMapper.map(book.get(), BookDTO.class);
            return ResponseEntity.ok(bookDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Custom-Header", "BookNotFound")
                    .body(null);
        }
    }

    // Add a new book
    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        bookList.add(book);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "BookAddedSuccessfully");

        return new ResponseEntity<>(bookDTO, headers, HttpStatus.CREATED);
    }

    // Update a book
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO updatedBookDTO) {
        Optional<Book> book = bookList.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();

        if (book.isPresent()) {
            Book updatedBook = modelMapper.map(updatedBookDTO, Book.class);
            bookList.set(bookList.indexOf(book.get()), updatedBook);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Custom-Header", "BookUpdatedSuccessfully");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(updatedBookDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .header("Custom-Header", "BookNotFound")
                    .body(null);
        }
    }

    // Delete a book
    @DeleteMapping("/{id}")
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

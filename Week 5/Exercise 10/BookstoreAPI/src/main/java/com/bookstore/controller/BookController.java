package com.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.bookstore.dto.BookDTO;
import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.util.MapperUtil;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOs = books.stream()
                .map(mapperUtil::toBookDTO)
                .toList();
        return ResponseEntity.ok(bookDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(value -> ResponseEntity.ok(mapperUtil.toBookDTO(value)))
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        Book book = mapperUtil.toBook(bookDTO);
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(mapperUtil.toBookDTO(savedBook));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Book book = mapperUtil.toBook(bookDTO);
        book.setId(id);
        Book updatedBook = bookRepository.save(book);
        return ResponseEntity.ok(mapperUtil.toBookDTO(updatedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

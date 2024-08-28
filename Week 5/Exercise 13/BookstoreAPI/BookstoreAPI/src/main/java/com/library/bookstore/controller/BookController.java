package com.library.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.library.bookstore.dto.BookDTO;
import com.library.bookstore.mapper.BookMapper;
import com.library.bookstore.model.Book;
import com.library.bookstore.repository.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @GetMapping
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                    .map(book -> {
                        BookDTO bookDTO = bookMapper.toDTO(book);
                        bookDTO.add(WebMvcLinkBuilder.linkTo(BookController.class).slash(bookDTO.getId()).withSelfRel());
                        return bookDTO;
                    }).toList();
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        Book book = bookMapper.toEntity(bookDTO);
        Book createdBook = bookRepository.save(book);
        BookDTO createdBookDTO = bookMapper.toDTO(createdBook);
        createdBookDTO.add(WebMvcLinkBuilder.linkTo(BookController.class).slash(createdBookDTO.getId()).withSelfRel());
        return ResponseEntity.status(201).body(createdBookDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(b -> {
            BookDTO bookDTO = bookMapper.toDTO(b);
            bookDTO.add(WebMvcLinkBuilder.linkTo(BookController.class).slash(bookDTO.getId()).withSelfRel());
            return ResponseEntity.ok(bookDTO);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        if (!bookRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Book book = bookMapper.toEntity(bookDTO);
        book.setId(id);
        Book updatedBook = bookRepository.save(book);
        BookDTO updatedBookDTO = bookMapper.toDTO(updatedBook);
        updatedBookDTO.add(WebMvcLinkBuilder.linkTo(BookController.class).slash(updatedBookDTO.getId()).withSelfRel());
        return ResponseEntity.ok(updatedBookDTO);
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

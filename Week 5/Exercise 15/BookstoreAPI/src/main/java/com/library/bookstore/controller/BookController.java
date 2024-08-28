package com.library.bookstore.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.EntityModel;
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
import com.library.bookstore.entity.Book;
import com.library.bookstore.repository.BookRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookController(BookRepository bookRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Get all books", description = "Fetch a list of all books")
    @GetMapping
    public ResponseEntity<List<EntityModel<BookDTO>>> getAllBooks() {
        List<EntityModel<BookDTO>> books = bookRepository.findAll().stream()
                .map(book -> EntityModel.of(modelMapper.map(book, BookDTO.class),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(book.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withRel("books")))
                .collect(Collectors.toList());
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Get a book by ID", description = "Fetch a book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved book"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BookDTO>> getBookById(
            @Parameter(description = "ID of the book to be retrieved", required = true)
            @PathVariable Long id) {
        return bookRepository.findById(id)
                .map(book -> EntityModel.of(modelMapper.map(book, BookDTO.class),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getBookById(book.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class).getAllBooks()).withRel("books")))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new book", description = "Add a new book to the collection")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created book"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<BookDTO> createBook(
            @RequestBody(description = "Book to be created", required = true)
            @io.swagger.v3.oas.annotations.parameters.RequestBody BookDTO bookDTO) {
        Book book = modelMapper.map(bookDTO, Book.class);
        book = bookRepository.save(book);
        return ResponseEntity.status(201).body(modelMapper.map(book, BookDTO.class));
    }

    @Operation(summary = "Update an existing book", description = "Update an existing book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated book"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(
            @Parameter(description = "ID of the book to be updated", required = true)
            @PathVariable Long id,
            @RequestBody(description = "Updated book data", required = true)
            BookDTO bookDTO) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(bookDTO.getTitle());
                    existingBook.setAuthor(bookDTO.getAuthor());
                    existingBook.setPrice(bookDTO.getPrice());
                    existingBook.setIsbn(bookDTO.getIsbn());
                    bookRepository.save(existingBook);
                    return ResponseEntity.ok(modelMapper.map(existingBook, BookDTO.class));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a book", description = "Remove a book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted book"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID of the book to be deleted", required = true)
            @PathVariable Long id) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    bookRepository.delete(existingBook);
                    return ResponseEntity.noContent().build();  // Ensures return type is ResponseEntity<Void>
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

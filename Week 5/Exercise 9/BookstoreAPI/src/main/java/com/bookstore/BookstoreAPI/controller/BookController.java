package com.bookstore.BookstoreAPI.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
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
import com.bookstore.BookstoreAPI.model.BookModel;
import com.bookstore.BookstoreAPI.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<EntityModel<BookModel>>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        List<EntityModel<BookModel>> models = books.stream()
                .map(bookDTO -> {
                    BookModel bookModel = new BookModel();
                    bookModel.setId(bookDTO.getId());
                    bookModel.setTitle(bookDTO.getTitle());
                    bookModel.setAuthor(bookDTO.getAuthor());
                    bookModel.setPrice(bookDTO.getPrice());
                    bookModel.setIsbn(bookDTO.getIsbn());
                    return EntityModel.of(bookModel,
                            linkTo(methodOn(BookController.class).getBookById(bookDTO.getId())).withSelfRel());
                }).collect(Collectors.toList());

        return ResponseEntity.ok(models);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<BookModel>> getBookById(@PathVariable Long id) {
        BookDTO bookDTO = bookService.getBookById(id);
        if (bookDTO == null) {
            return ResponseEntity.notFound().build();
        }

        BookModel bookModel = new BookModel();
        bookModel.setId(bookDTO.getId());
        bookModel.setTitle(bookDTO.getTitle());
        bookModel.setAuthor(bookDTO.getAuthor());
        bookModel.setPrice(bookDTO.getPrice());
        bookModel.setIsbn(bookDTO.getIsbn());

        EntityModel<BookModel> resource = EntityModel.of(bookModel);
        resource.add(linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return ResponseEntity.created(linkTo(methodOn(BookController.class).getBookById(createdBook.getId())).toUri()).body(createdBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.updateBook(id, bookDTO);
        if (updatedBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}

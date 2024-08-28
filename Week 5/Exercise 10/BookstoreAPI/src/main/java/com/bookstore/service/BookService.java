package com.bookstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.dto.BookDTO;
import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.util.MapperUtil;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MapperUtil mapperUtil;

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(mapperUtil::toBookDTO)
                .toList();
    }

    public BookDTO getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(mapperUtil::toBookDTO)
                   .orElse(null);
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = mapperUtil.toBook(bookDTO);
        Book savedBook = bookRepository.save(book);
        return mapperUtil.toBookDTO(savedBook);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        if (!bookRepository.existsById(id)) {
            return null;
        }
        Book book = mapperUtil.toBook(bookDTO);
        book.setId(id);
        Book updatedBook = bookRepository.save(book);
        return mapperUtil.toBookDTO(updatedBook);
    }

    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        }
    }
}

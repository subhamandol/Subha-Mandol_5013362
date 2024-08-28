package com.library.bookstore;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.library.bookstore.entity.Book;
import com.library.bookstore.repository.BookRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    public void setup() {
        bookRepository.deleteAll(); // Clean up the database before each test
        // Add some sample data
        Book book1 = new Book(1L, "Book One", "Author One", "1234567890123", 100.00);
        Book book2 = new Book(2L, "Book Two", "Author Two", "1234567890124", 150.00);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        // Perform the GET request
        ResultActions response = mockMvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON));

        // Expect status to be OK and list size to be 2
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    public void testGetBookById() throws Exception {
        // Perform the GET request
        ResultActions response = mockMvc.perform(get("/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        // Expect status to be OK and return book with ID 1
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Book One"));
    }

    @Test
    public void testCreateBook() throws Exception {
        String newBookJson = "{ \"title\": \"New Book\", \"author\": \"New Author\", \"isbn\": \"9876543210987\", \"price\": 120.00 }";

        // Perform the POST request
        ResultActions response = mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(newBookJson));

        // Expect status to be Created and the book to be in the database
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Book"));

        // Check that the book is now in the database
        ResultActions getResponse = mockMvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON));

        getResponse.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3)); // Now there should be 3 books
    }

    @Test
    public void testDeleteBook() throws Exception {
        // Perform the DELETE request
        ResultActions response = mockMvc.perform(delete("/books/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON));

        // Expect status to be No Content
        response.andExpect(status().isNoContent());

        // Check that the book was deleted from the database
        ResultActions getResponse = mockMvc.perform(get("/books")
                .contentType(MediaType.APPLICATION_JSON));

        getResponse.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1)); // Now there should be only 1 book left
    }
}

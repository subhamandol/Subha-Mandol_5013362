package com.bookstore.BookstoreAPI.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookModel extends RepresentationModel<BookModel> {

    private Long id;
    private String title;
    private String author;
    private double price;
    private String isbn;
}

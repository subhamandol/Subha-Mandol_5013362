package com.library.bookstore.mapper;

import org.mapstruct.Mapper;

import com.library.bookstore.dto.BookDTO;
import com.library.bookstore.model.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDTO(Book book);
    Book toEntity(BookDTO bookDTO);
}

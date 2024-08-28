package com.bookstore.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.bookstore.dto.BookDTO;
import com.bookstore.dto.CustomerDTO;
import com.bookstore.entity.Book;
import com.bookstore.entity.Customer;

@Component
public class MapperUtil {

    private final ModelMapper modelMapper = new ModelMapper();

    public BookDTO toBookDTO(Book book) {
        return modelMapper.map(book, BookDTO.class);
    }

    public Book toBook(BookDTO bookDTO) {
        return modelMapper.map(bookDTO, Book.class);
    }

    public CustomerDTO toCustomerDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public Customer toCustomer(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }
}

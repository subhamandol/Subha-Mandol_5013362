package com.bookstore.BookstoreAPI.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class CustomerModel extends RepresentationModel<CustomerModel> {

    private Long id;
    private String name;
    private String email;
}

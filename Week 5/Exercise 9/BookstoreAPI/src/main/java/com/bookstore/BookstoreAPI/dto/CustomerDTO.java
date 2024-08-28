package com.bookstore.BookstoreAPI.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}

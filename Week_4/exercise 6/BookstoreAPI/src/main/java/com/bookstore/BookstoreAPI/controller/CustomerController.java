package com.bookstore.BookstoreAPI.controller;

import com.bookstore.BookstoreAPI.model.Customer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private List<Customer> customerList = new ArrayList<>();

    // 1. Request Body - JSON
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        customerList.add(customer);
        return customer;
    }

    // 2. Form Data
    @PostMapping("/register")
    public Customer registerCustomer(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam(required = false) MultipartFile profilePicture) {

        // Handling profile picture upload logic (if any)
        if (profilePicture != null && !profilePicture.isEmpty()) {
            // Process the profile picture (e.g., save it somewhere)
        }

        Customer customer = new Customer();
        customer.setId((long) (customerList.size() + 1)); // Simple auto-increment ID logic
        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);

        customerList.add(customer);
        return customer;
    }
}

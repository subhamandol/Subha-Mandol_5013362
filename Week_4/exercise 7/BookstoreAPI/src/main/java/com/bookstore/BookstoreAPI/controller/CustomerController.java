/*package com.bookstore.BookstoreAPI.controller;

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
*/
// Updated CustomerController.java
package com.bookstore.BookstoreAPI.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookstore.BookstoreAPI.dto.CustomerDTO;
import com.bookstore.BookstoreAPI.model.Customer;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private List<Customer> customerList = new ArrayList<>();
    private final ModelMapper modelMapper;

    public CustomerController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // Get all customers
    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerList.stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    // Get a customer by ID
    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerList.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        return customer.map(value -> modelMapper.map(value, CustomerDTO.class)).orElse(null);
    }

    // Add a new customer
    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customerList.add(customer);
        return customerDTO;
    }

    // Register a customer with form data
    @PostMapping("/register")
    public CustomerDTO registerCustomer(
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
        return modelMapper.map(customer, CustomerDTO.class);
    }
}

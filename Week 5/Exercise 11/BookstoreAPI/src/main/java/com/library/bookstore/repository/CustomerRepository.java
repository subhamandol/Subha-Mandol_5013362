package com.library.bookstore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.bookstore.entity.Customer;
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find a customer by email
    Optional<Customer> findByEmail(String email);
    
    // Find a customer by name
    List<Customer> findByName(String name);
}

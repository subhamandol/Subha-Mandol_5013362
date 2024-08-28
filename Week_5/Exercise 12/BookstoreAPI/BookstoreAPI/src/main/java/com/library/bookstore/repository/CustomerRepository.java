package com.library.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.bookstore.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

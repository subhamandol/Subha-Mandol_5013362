package com.bookstore.BookstoreAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.BookstoreAPI.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}

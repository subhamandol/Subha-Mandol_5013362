package com.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.dto.CustomerDTO;
import com.bookstore.entity.Customer;
import com.bookstore.repository.CustomerRepository;
import com.bookstore.util.MapperUtil;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(mapperUtil::toCustomerDTO)
                .toList();
        return ResponseEntity.ok(customerDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(value -> ResponseEntity.ok(mapperUtil.toCustomerDTO(value)))
                       .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = mapperUtil.toCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(mapperUtil.toCustomerDTO(savedCustomer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        if (!customerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Customer customer = mapperUtil.toCustomer(customerDTO);
        customer.setId(id);
        Customer updatedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(mapperUtil.toCustomerDTO(updatedCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        if (!customerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

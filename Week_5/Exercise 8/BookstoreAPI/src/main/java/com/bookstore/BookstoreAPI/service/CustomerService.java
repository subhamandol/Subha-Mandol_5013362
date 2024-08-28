package com.bookstore.BookstoreAPI.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bookstore.BookstoreAPI.config.ModelMapperConfig;
import com.bookstore.BookstoreAPI.dto.CustomerDTO;
import com.bookstore.BookstoreAPI.model.Customer;
import com.bookstore.BookstoreAPI.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerRepository customerRepository, ModelMapperConfig modelMapperConfig) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapperConfig.modelMapper();
    }

    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer = customerRepository.save(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(value -> modelMapper.map(value, CustomerDTO.class)).orElse(null);
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(customer -> modelMapper.map(customer, CustomerDTO.class))
                .collect(Collectors.toList());
    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setId(id);
        customer = customerRepository.save(customer);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}

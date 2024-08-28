package com.bookstore.BookstoreAPI.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.BookstoreAPI.dto.CustomerDTO;
import com.bookstore.BookstoreAPI.model.CustomerModel;
import com.bookstore.BookstoreAPI.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<EntityModel<CustomerModel>>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        List<EntityModel<CustomerModel>> models = customers.stream()
                .map(customerDTO -> {
                    CustomerModel customerModel = new CustomerModel();
                    customerModel.setId(customerDTO.getId());
                    customerModel.setName(customerDTO.getName());
                    customerModel.setEmail(customerDTO.getEmail());
                    return EntityModel.of(customerModel,
                            linkTo(methodOn(CustomerController.class).getCustomerById(customerDTO.getId())).withSelfRel());
                }).collect(Collectors.toList());

        return ResponseEntity.ok(models);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CustomerModel>> getCustomerById(@PathVariable Long id) {
        CustomerDTO customerDTO = customerService.getCustomerById(id);
        if (customerDTO == null) {
            return ResponseEntity.notFound().build();
        }

        CustomerModel customerModel = new CustomerModel();
        customerModel.setId(customerDTO.getId());
        customerModel.setName(customerDTO.getName());
        customerModel.setEmail(customerDTO.getEmail());

        EntityModel<CustomerModel> resource = EntityModel.of(customerModel);
        resource.add(linkTo(methodOn(CustomerController.class).getCustomerById(id)).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.created(linkTo(methodOn(CustomerController.class).getCustomerById(createdCustomer.getId())).toUri()).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(id, customerDTO);
        if (updatedCustomer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}

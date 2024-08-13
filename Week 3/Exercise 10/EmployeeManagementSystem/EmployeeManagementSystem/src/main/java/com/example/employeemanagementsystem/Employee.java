package com.example.employeemanagementsystem;

import java.time.LocalDateTime;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "employees")
@BatchSize(size = 20) // Example of batch size for fetching
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_name", nullable = false) // Custom column name
    private String name;

    @Column(name = "employee_email", unique = true) // Unique constraint
    private String email;

    @Column(name = "department_name")
    private String department;

    @CreationTimestamp // Hibernate-specific annotation for automatic timestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp // Hibernate-specific annotation for automatic timestamp
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;
}

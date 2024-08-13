package com.example.employeemanagementsystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Example of a derived query method to find employees by department
    List<Employee> findByDepartment(String department);
}

package com.example.employeemanagementsystem;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Derived Query Methods
    List<Employee> findByDepartment(String department);

    List<Employee> findByNameContaining(String name);

    // Custom Query Method using @Query
    @Query("SELECT e FROM Employee e WHERE e.email = ?1")
    Employee findByEmail(String email);

    // Custom Query with Named Parameter
    @Query("SELECT e FROM Employee e WHERE e.department = :department")
    List<Employee> findByDepartmentNamed(@Param("department") String department);

    // Pagination and Sorting
    Page<Employee> findByDepartment(String department, Pageable pageable);
}

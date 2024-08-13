package com.example.employeemanagementsystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Named Query method
    @Query(name = "Employee.findByEmailNamed")
    Employee findByEmailNamed(@Param("email") String email);

    @Query(name = "Employee.findByDepartmentNamed")
    List<Employee> findByDepartmentNamed(@Param("department") String department);
}

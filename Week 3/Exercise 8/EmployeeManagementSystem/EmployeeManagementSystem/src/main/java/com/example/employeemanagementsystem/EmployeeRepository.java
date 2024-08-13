package com.example.employeemanagementsystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Interface-based projection
    @Query("SELECT e FROM Employee e WHERE e.department = :department")
    List<EmployeeProjection> findEmployeeProjectionsByDepartment(@Param("department") String department);

    // Class-based projection
    @Query("SELECT new com.example.employeemanagementsystem.EmployeeDTO(e.id, e.name, e.email) FROM Employee e WHERE e.department = :department")
    List<EmployeeDTO> findEmployeeDTOsByDepartment(@Param("department") String department);
}

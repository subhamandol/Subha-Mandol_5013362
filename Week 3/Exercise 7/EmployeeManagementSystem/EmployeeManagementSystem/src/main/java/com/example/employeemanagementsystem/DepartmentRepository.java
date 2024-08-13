package com.example.employeemanagementsystem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Named Query method
    @Query(name = "Department.findByNameNamed")
    Department findByNameNamed(@Param("name") String name);
}


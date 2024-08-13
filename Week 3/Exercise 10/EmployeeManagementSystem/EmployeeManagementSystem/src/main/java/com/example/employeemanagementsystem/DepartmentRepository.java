package com.example.employeemanagementsystem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Interface-based projection
    @Query("SELECT d FROM Department d WHERE d.name = :name")
    List<DepartmentProjection> findDepartmentProjectionsByName(@Param("name") String name);

    // Class-based projection
    @Query("SELECT new com.example.employeemanagementsystem.DepartmentDTO(d.id, d.name) FROM Department d WHERE d.name = :name")
    List<DepartmentDTO> findDepartmentDTOsByName(@Param("name") String name);
}

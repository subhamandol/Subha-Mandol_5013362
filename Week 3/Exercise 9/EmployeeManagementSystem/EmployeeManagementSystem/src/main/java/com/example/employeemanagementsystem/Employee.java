package com.example.employeemanagementsystem;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@NamedQueries({
    @NamedQuery(
        name = "Employee.findByEmailNamed",
        query = "SELECT e FROM Employee e WHERE e.email = :email"
    ),
    @NamedQuery(
        name = "Employee.findByDepartmentNamed",
        query = "SELECT e FROM Employee e WHERE e.department = :department"
    )
})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String department;
}

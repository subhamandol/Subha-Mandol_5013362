package com.example.employeemanagementsystem;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public void saveEmployeesInBatch(List<Employee> employees) {
        final int batchSize = 30; // Matches the batch size in properties

        for (int i = 0; i < employees.size(); i++) {
            employeeRepository.save(employees.get(i));
            if (i % batchSize == 0 && i > 0) {
                employeeRepository.flush();
                //employeeRepository.clear(); // Clear session to avoid memory issues
            }
        }
    }
}

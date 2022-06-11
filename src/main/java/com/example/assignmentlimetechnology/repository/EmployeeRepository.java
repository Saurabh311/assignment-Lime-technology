package com.example.assignmentlimetechnology.repository;

import com.example.assignmentlimetechnology.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByName(String name);
}

package com.example.assignmentlimetechnology.repository;

import com.example.assignmentlimetechnology.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findById(BigInteger id);
}
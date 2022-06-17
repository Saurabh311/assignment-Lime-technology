package com.example.assignmentlimetechnology.Exception;

public class EmployeeNotFoundException extends Throwable {
    public EmployeeNotFoundException(String id) {
        super(id);
    }
}

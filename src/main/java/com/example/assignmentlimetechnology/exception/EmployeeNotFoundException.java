package com.example.assignmentlimetechnology.exception;

public class EmployeeNotFoundException extends Throwable {
    public EmployeeNotFoundException(String id) {
        super(id);
    }
}

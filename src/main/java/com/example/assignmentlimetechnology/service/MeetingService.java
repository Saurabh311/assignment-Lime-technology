package com.example.assignmentlimetechnology.service;

import com.example.assignmentlimetechnology.entity.MeetingRequest;
import com.example.assignmentlimetechnology.repository.EmployeeRepository;
import com.example.assignmentlimetechnology.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;


@Service
public class MeetingService {
    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public Boolean meetingSlotAvailable(){
        return true;
    }


}

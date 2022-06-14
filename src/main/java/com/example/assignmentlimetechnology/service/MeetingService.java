package com.example.assignmentlimetechnology.service;

import com.example.assignmentlimetechnology.entity.Meeting;
import com.example.assignmentlimetechnology.entity.MeetingRequest;
import com.example.assignmentlimetechnology.repository.EmployeeRepository;
import com.example.assignmentlimetechnology.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class MeetingService {
    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public Boolean meetingSlotAvailable(){
        return true;
    }
    public List<Meeting> getAllMeeting() {
        List<Meeting> meetings = new ArrayList<Meeting>();
        meetingRepository.findAll().forEach(meeting -> meetings.add(meeting));
        return meetings;
    }

    public void saveOrUpdate(Meeting meeting) {
        meetingRepository.save(meeting);
    }
}

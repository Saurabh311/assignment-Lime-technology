package com.example.assignmentlimetechnology.controller;

import com.example.assignmentlimetechnology.entity.Employee;
import com.example.assignmentlimetechnology.entity.Meeting;
import com.example.assignmentlimetechnology.entity.MeetingRequest;
import com.example.assignmentlimetechnology.service.MeetingService;
import com.example.assignmentlimetechnology.repository.EmployeeRepository;
import com.example.assignmentlimetechnology.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class MeetingController {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    MeetingRepository meetingRepository;

    @Autowired
    MeetingService meetingService;

    @GetMapping("/welcome")
    public String welcome() throws Exception{
        return "Welcome to Lime";
    }

    @GetMapping(value = "/data")
    public String getData(){
        String url = "https://builds.lundalogik.com/api/v1/builds/freebusy/versions/1.0.0/file";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
        return result;
    }

    @PostMapping(value = "/meeting")
    public ResponseEntity<?> newMeeting(@RequestBody MeetingRequest meetingRequest) {
        Employee employee = employeeRepository.findById(meetingRequest.getUserId());
        if (employee == null ){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (meetingService.meetingSlotAvailable()){
            Meeting newMeeting = Meeting.builder()
                    .meetingStartDateTime(meetingRequest.getStartMeetingDateTime())
                    .meetingEndDateTime(meetingRequest.getEndMeetingDateTime())
                    .employeeId(meetingRequest.getUserId())
                    .build();
            meetingRepository.save(newMeeting);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

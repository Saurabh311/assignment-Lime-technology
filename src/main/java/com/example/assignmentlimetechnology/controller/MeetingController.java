package com.example.assignmentlimetechnology.controller;

import com.example.assignmentlimetechnology.exception.EmployeeNotFoundException;
import com.example.assignmentlimetechnology.entity.MeetingRequest;
import com.example.assignmentlimetechnology.entity.MeetingSlot;
import com.example.assignmentlimetechnology.service.MeetingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;

@RestController
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @PostMapping(value = "/meetingEntry")
    public ArrayList<MeetingSlot> newMeeting(@RequestBody MeetingRequest meetingRequest) throws Exception, EmployeeNotFoundException {
        ArrayList<MeetingSlot> meetingSlots = meetingService.returnResult(meetingRequest);
        return meetingSlots;
    }    
}

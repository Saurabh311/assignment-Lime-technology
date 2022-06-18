package com.example.assignmentlimetechnology.entity;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MeetingRequest {
    private ArrayList<String> employeeIdList;
    private String startMeetingDateTime;
    private String endMeetingDateTime;
    private Long meetingLength;
    private String officeStartTime;
    private String officeEndTime;
}

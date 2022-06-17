package com.example.assignmentlimetechnology.entity;

import lombok.Data;

@Data
public class MeetingRequest {
    private String employeeIdList;
    private String startMeetingDateTime;
    private String endMeetingDateTime;
    private Long meetingLength;
    private String officeStartTime;
    private String officeEndTime;
}

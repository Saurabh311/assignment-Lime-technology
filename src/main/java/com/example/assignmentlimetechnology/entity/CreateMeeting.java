package com.example.assignmentlimetechnology.entity;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class CreateMeeting {
    String employeeID;
    LocalDateTime startDatetimeMeeting;
    LocalDateTime endDateTimeMeeting;
}

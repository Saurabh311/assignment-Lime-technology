package com.example.assignmentlimetechnology.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CreateMeeting {
    String employeeID;
    LocalDateTime startDatetimeMeeting;
    LocalDateTime endDateTimeMeeting;
}

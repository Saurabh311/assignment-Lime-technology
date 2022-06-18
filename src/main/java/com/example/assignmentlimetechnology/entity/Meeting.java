package com.example.assignmentlimetechnology.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Builder
@Data
public class Meeting {
    String employeeID;
    LocalDateTime startDatetimeMeeting;
    LocalDateTime endDateTimeMeeting;
}

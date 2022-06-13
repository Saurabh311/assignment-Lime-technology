package com.example.assignmentlimetechnology.entity;

import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MeetingRequest {
    private LocalDateTime startMeetingDateTime;
    private LocalDateTime endMeetingDateTime;
    private BigInteger userId;
}

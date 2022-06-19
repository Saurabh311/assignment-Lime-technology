package com.example.assignmentlimetechnology.entity;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class MeetingSlot {
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
}

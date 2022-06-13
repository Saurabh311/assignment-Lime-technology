package com.example.assignmentlimetechnology.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column (nullable = false)
    private LocalDateTime meetingStartDateTime;

    @Column (nullable = false)
    private LocalDateTime meetingEndDateTime;

    @Column (nullable = false)
    private BigInteger employeeId;
}

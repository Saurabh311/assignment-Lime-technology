package com.example.assignmentlimetechnology.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String meetingId;

    @Column (nullable = false)
    private LocalDateTime meetingStartDateTime;

    @Column (nullable = false)
    private LocalDateTime meetingEndDateTime;

    @Column (nullable = false, columnDefinition="TEXT", length = 1000)
    private String employeeId;
}

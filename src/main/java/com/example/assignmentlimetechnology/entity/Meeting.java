package com.example.assignmentlimetechnology.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (nullable = false)
    private LocalDate date;

    @Column (nullable = false)
    private LocalTime time;

    @ManyToOne(targetEntity = Employee.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "employee", nullable = false)
    @JsonIgnore
    private Employee customer;

    @Column(name = "employee", insertable = false, updatable = false)
    private Long employeeId;
}

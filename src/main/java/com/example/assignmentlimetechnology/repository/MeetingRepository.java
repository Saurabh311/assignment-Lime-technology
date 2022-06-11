package com.example.assignmentlimetechnology.repository;

import com.example.assignmentlimetechnology.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository {
    Meeting findById(String id);
}

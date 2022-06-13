package com.example.assignmentlimetechnology.repository;

import com.example.assignmentlimetechnology.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigInteger;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findMeetingByEmployeeId(BigInteger id);
}

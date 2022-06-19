package com.example.assignmentlimetechnology.service;

import com.example.assignmentlimetechnology.exception.EmployeeNotFoundException;
import com.example.assignmentlimetechnology.entity.MeetingRequest;
import com.example.assignmentlimetechnology.entity.MeetingSlot;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


@Service
public class MeetingService {
    // function for handling http input and returning the result as response
    public ArrayList<MeetingSlot> returnResult(MeetingRequest meetingRequest) throws Exception, EmployeeNotFoundException {
        DataService dataDownloader = new DataService();

        ArrayList<String> employeeID = meetingRequest.getEmployeeIdList();
        LocalDateTime startMeetingDateTime = dataDownloader.localDateTimeFormatter(meetingRequest.getStartMeetingDateTime());
        LocalDateTime endMeetingDateTime = dataDownloader.localDateTimeFormatter(meetingRequest.getEndMeetingDateTime());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
        LocalTime officeStartTime = LocalTime.parse(meetingRequest.getOfficeStartTime(), formatter);
        LocalTime officeEndTime = LocalTime.parse(meetingRequest.getOfficeEndTime(), formatter);

        return dataDownloader.displayAvailableSlots(
                employeeID,
                startMeetingDateTime,
                endMeetingDateTime,
                meetingRequest.getMeetingLength(),
                officeStartTime,
                officeEndTime
        );
    }
}

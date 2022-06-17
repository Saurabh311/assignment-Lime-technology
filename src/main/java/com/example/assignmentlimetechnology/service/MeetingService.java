package com.example.assignmentlimetechnology.service;

import com.example.assignmentlimetechnology.entity.MeetingRequest;
import com.example.assignmentlimetechnology.entity.MeetingSlot;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


@Service
public class MeetingService {

    public ArrayList<MeetingSlot> returnResult(MeetingRequest meetingRequest) throws Exception {

        ArrayList<String> employeeID = new ArrayList<>();
        employeeID.add(meetingRequest.getEmployeeIdList());

        DataDownloader dataDownloader = new DataDownloader();
        LocalDateTime startMeetingDateTime = dataDownloader.dateTimeFormat(meetingRequest.getStartMeetingDateTime());
        LocalDateTime endMeetingDateTime = dataDownloader.dateTimeFormat(meetingRequest.getEndMeetingDateTime());

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

package com.example.assignmentlimetechnology.controller;

import com.example.assignmentlimetechnology.Exception.EmployeeNotFoundException;
import com.example.assignmentlimetechnology.entity.MeetingRequest;
import com.example.assignmentlimetechnology.entity.MeetingSlot;
import com.example.assignmentlimetechnology.service.MeetingService;
import com.example.assignmentlimetechnology.service.DataDownloader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;


@RestController
public class MeetingController {
    @Autowired
    private MeetingService meetingService;

    @Autowired
    private DataDownloader dataDownloader;

    @PostMapping(value = "/meetingEntry")
    public ArrayList<MeetingSlot> newMeeting(@RequestBody MeetingRequest meetingRequest) throws Exception, EmployeeNotFoundException {
        ArrayList<MeetingSlot> meetingSlots = meetingService.returnResult(meetingRequest);
        return meetingSlots;
    }

    @PostMapping(value = "/data")
    public void getData(){
        String url = "https://builds.lundalogik.com/api/v1/builds/freebusy/versions/1.0.0/file";
        String fileLocation = "src\\main\\resources\\bookedMeetingFile.txt";
        try {
            dataDownloader.downloadData(url, fileLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

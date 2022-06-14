package com.example.assignmentlimetechnology.controller;

import com.example.assignmentlimetechnology.entity.Employee;
import com.example.assignmentlimetechnology.entity.Meeting;
import com.example.assignmentlimetechnology.entity.MeetingRequest;
import com.example.assignmentlimetechnology.service.MeetingService;
import com.example.assignmentlimetechnology.repository.EmployeeRepository;
import com.example.assignmentlimetechnology.repository.MeetingRepository;
import com.example.assignmentlimetechnology.service.DataDownloader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MeetingController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private DataDownloader dataDownloader;

    @GetMapping("/welcome")
    public String welcome() throws Exception{
        return "Welcome to Lime";
    }
    @GetMapping("/meeting")
    private List<Meeting> getAllStudent(){
        return meetingService.getAllMeeting();
    }

    @PostMapping("/meeting")
    private void saveMeeting(@RequestBody Meeting meeting) {
        meetingService.saveOrUpdate(meeting);
    }




    @PostMapping(value = "/data")
    public void getData(){
        String url = "https://builds.lundalogik.com/api/v1/builds/freebusy/versions/1.0.0/file";
        String fileLocation = "src\\main\\resources\\bookedMeetingFile.txt";
        try {
            dataDownloader.downloadData(url, fileLocation);
            //dataDownloader.readingData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*String url = "https://builds.lundalogik.com/api/v1/builds/freebusy/versions/1.0.0/file";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
        return result;*/
    }

    @PostMapping(value = "/meetingEntry")
    public ResponseEntity<?> newMeeting(@RequestBody MeetingRequest meetingRequest) {
        Employee employee = employeeRepository.findById(meetingRequest.getUserId());
        if (employee == null ){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        if (meetingService.meetingSlotAvailable()){
            Meeting newMeeting = Meeting.builder()
                    .meetingStartDateTime(meetingRequest.getStartMeetingDateTime())
                    .meetingEndDateTime(meetingRequest.getEndMeetingDateTime())
                    .build();
            meetingRepository.save(newMeeting);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

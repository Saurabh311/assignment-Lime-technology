package com.example.assignmentlimetechnology;

import com.example.assignmentlimetechnology.Exception.EmployeeNotFoundException;
import com.example.assignmentlimetechnology.entity.Meeting;
import com.example.assignmentlimetechnology.service.DataDownloader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class AssignmentLimeTechnologyApplicationTests {
    @BeforeEach
    void setUp() {
        DataDownloader dataDownloader = new DataDownloader();
    }

    @Test
    public void test_JUnit() {
        System.out.println("This is the testcase in this class");
        String str1="This is the testcase in this class";
        assertEquals("This is the testcase in this class", str1);
    }

    @Test
    public void test_fetch_employee_data() throws Exception, EmployeeNotFoundException {
        DataDownloader dataDownloader = new DataDownloader();
        ArrayList<Meeting> employeeData = dataDownloader.fetchEmployeeData("325936446199886750546547952200356331252");
        Assertions.assertNotNull(employeeData);
        assertEquals(64, employeeData.size());
    }

    @Test
    public void test_fetch_employee_data_with_wrong_id() throws Exception, EmployeeNotFoundException {
        DataDownloader dataDownloader = new DataDownloader();
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            ArrayList<Meeting> employeeData = dataDownloader.fetchEmployeeData("wrongID");
        });
    }

    @Test
    public void test_max_possible_time_slots() throws Exception, EmployeeNotFoundException {
        DataDownloader dataDownloader = new DataDownloader();
        //ArrayList<CreateMeeting> employeeData = dataDownloader.fetchEmployeeData("311550489458349670602624217128768553177");

        String employeeIdList = "311550489458349670602624217128768553177";
        String startDateTime = "2/17/2015 8:00:00 AM";
        String endDateTime = "2/27/2015 8:00:00 PM";
        Long meetingLength = Long.valueOf(60);
        //String officeTimeStart = "08.00";
        //String officeTimeEnd = "17.00";
        ArrayList<String> employeeID = new ArrayList<>();
        //employeeID.add(employeeIdList);

        LocalDateTime startMeetingDateTime = dataDownloader.localDateTimeFormatter(startDateTime);
        LocalDateTime endMeetingDateTime = dataDownloader.localDateTimeFormatter(endDateTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
        //LocalTime officeStartTime = LocalTime.parse(officeTimeStart, formatter);
        //LocalTime officeEndTime = LocalTime.parse(officeTimeEnd, formatter);

        ArrayList<Meeting> bookedMeetings = dataDownloader.getBookedMeetingsRelatedTimeSlots(employeeID,
                startMeetingDateTime,endMeetingDateTime);
        Assertions.assertNotNull(bookedMeetings);
        assertTrue(bookedMeetings.size() > 0);




    }





}

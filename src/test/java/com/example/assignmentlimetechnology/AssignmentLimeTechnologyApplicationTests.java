package com.example.assignmentlimetechnology;

import com.example.assignmentlimetechnology.entity.MeetingSlot;
import com.example.assignmentlimetechnology.exception.EmployeeNotFoundException;
import com.example.assignmentlimetechnology.entity.Meeting;
import com.example.assignmentlimetechnology.service.DataService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AssignmentLimeTechnologyApplicationTests {
    DataService dataDownloader;

    @BeforeEach
    void setUp() {
        dataDownloader = new DataService();
    }

    @Test
    public void test_fetch_employee_data() throws Exception, EmployeeNotFoundException {
        ArrayList<Meeting> employeeData = dataDownloader.getEmployeeData("123");
        Assertions.assertNotNull(employeeData);
        assertEquals(7, employeeData.size());
    }

    @Test
    public void test_fetch_employee_data_with_wrong_id() {
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            ArrayList<Meeting> employeeData = dataDownloader.getEmployeeData("wrongID");
        });
    }

    @Test
    public void test_max_possible_time_slots() throws Exception, EmployeeNotFoundException {
        String employeeIdList = "123";
        String startDateTime = "3/16/2015 8:00:00 AM";
        String endDateTime = "3/16/2015 8:00:00 PM";
        ArrayList<String> employeeID = new ArrayList<>();
        employeeID.add(employeeIdList);

        LocalDateTime startMeetingDateTime = dataDownloader.localDateTimeFormatter(startDateTime);
        LocalDateTime endMeetingDateTime = dataDownloader.localDateTimeFormatter(endDateTime);

        ArrayList<Meeting> bookedMeetings = dataDownloader.getBookedMeetingsBtwTimeSlots(employeeID,
                startMeetingDateTime,endMeetingDateTime);
        Assertions.assertNotNull(bookedMeetings);
        assertEquals(2, bookedMeetings.size());
    }


    @Test
    public void get_available_slots_without_office_hours_filter() throws Exception, EmployeeNotFoundException {
        String employeeIdList = "123";
        String startDateTime = "3/16/2015 8:00:00 AM";
        String endDateTime = "3/16/2015 8:00:00 PM";
        Long meetingLength = 60L;
        ArrayList<String> employeeID = new ArrayList<>();
        employeeID.add(employeeIdList);
        LocalDateTime startMeetingDateTime = dataDownloader.localDateTimeFormatter(startDateTime);
        LocalDateTime endMeetingDateTime = dataDownloader.localDateTimeFormatter(endDateTime);

        ArrayList<Meeting> bookedMeetings = dataDownloader.getBookedMeetingsBtwTimeSlots(employeeID,
                startMeetingDateTime,endMeetingDateTime);

        ArrayList<MeetingSlot> meetingSlots = dataDownloader.getPossibleMeetingSlots(bookedMeetings,
                startMeetingDateTime, endMeetingDateTime, meetingLength );
        assertEquals(9, meetingSlots.size());
    }

    @Test
    public void get_available_slots_after_office_hours_filter() throws Exception, EmployeeNotFoundException {
        String employeeIdList = "123";
        String startDateTime = "3/16/2015 8:00:00 AM";
        String endDateTime = "3/16/2015 8:00:00 PM";
        Long meetingLength = 60L;
        String officeTimeStart = "08.00";
        String officeTimeEnd = "17.00";
        ArrayList<String> employeeID = new ArrayList<>();
        employeeID.add(employeeIdList);

        LocalDateTime startMeetingDateTime = dataDownloader.localDateTimeFormatter(startDateTime);
        LocalDateTime endMeetingDateTime = dataDownloader.localDateTimeFormatter(endDateTime);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
        LocalTime officeStartTime = LocalTime.parse(officeTimeStart, formatter);
        LocalTime officeEndTime = LocalTime.parse(officeTimeEnd, formatter);

        ArrayList<Meeting> bookedMeetings = dataDownloader.getBookedMeetingsBtwTimeSlots(employeeID,
                startMeetingDateTime,endMeetingDateTime);

        ArrayList<MeetingSlot> meetingSlots = dataDownloader.getPossibleMeetingSlots(bookedMeetings,
                startMeetingDateTime, endMeetingDateTime, meetingLength );
        assertEquals(9, meetingSlots.size());

        ArrayList<MeetingSlot> finalMeetingSlots = dataDownloader.officeHourFilter(meetingSlots,
                officeStartTime, officeEndTime );
        assertEquals(6, finalMeetingSlots.size());
    }

}

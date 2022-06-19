package com.example.assignmentlimetechnology.service;

import com.example.assignmentlimetechnology.exception.EmployeeNotFoundException;
import com.example.assignmentlimetechnology.entity.Meeting;
import com.example.assignmentlimetechnology.entity.MeetingSlot;
import org.springframework.stereotype.Service;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DataService {
    // function or fetching the latest booked meeting data from API and saving Local text file.
    public void downloadData(String apiUrl, String file) throws Exception {
        URL url = new URL(apiUrl);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        var count = 0;
        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    // fetching all detail of particular employee data from the saved meeting data.
    public ArrayList<Meeting> fetchEmployeeData(String id) throws Exception, EmployeeNotFoundException {
        File file = new File("src\\main\\resources\\bookedMeetingFile.txt");
        Scanner sc = new Scanner(file);
        ArrayList<Meeting> meetings = new ArrayList<>();
        String[] dataLine;
        while (sc.hasNextLine()) {
            dataLine = sc.nextLine().split(";");
            if (dataLine.length == 4 && dataLine[0].equals(id)) {
                LocalDateTime startMeetingDateTime = localDateTimeFormatter(dataLine[1]);
                LocalDateTime endMeetingDateTime = localDateTimeFormatter(dataLine[2]);
                if (startMeetingDateTime != null && endMeetingDateTime != null) {
                    Meeting createMeeting = Meeting.builder()
                            .employeeID(dataLine[0])
                            .startDatetimeMeeting(startMeetingDateTime)
                            .endDateTimeMeeting(endMeetingDateTime)
                            .build();
                    meetings.add(createMeeting);
                }
            }
        }
        // Add hardcode data as well for unit testing and scenario base functional testing
        File testCaseData= new File("src\\main\\resources\\testCase.txt");
        Scanner testCase = new Scanner(testCaseData);
        String[] HardcodeDataLine;
        while (testCase.hasNextLine()) {
            HardcodeDataLine = testCase.nextLine().split(";");
            if (HardcodeDataLine.length == 4 && HardcodeDataLine[0].equals(id)) {
                LocalDateTime startMeetingDateTime = localDateTimeFormatter(HardcodeDataLine[1]);
                LocalDateTime endMeetingDateTime = localDateTimeFormatter(HardcodeDataLine[2]);
                if (startMeetingDateTime != null && endMeetingDateTime != null) {
                    Meeting createMeeting = Meeting.builder()
                            .employeeID(HardcodeDataLine[0])
                            .startDatetimeMeeting(startMeetingDateTime)
                            .endDateTimeMeeting(endMeetingDateTime)
                            .build();
                    meetings.add(createMeeting);
                }
            }
        }
        if (meetings.size() > 0) {
            return meetings;
        }
        throw new EmployeeNotFoundException(id + " is not found.");
    }

    // Getting the booked meeting data of employees/employee between desired meeting date and times.
    public ArrayList<Meeting> getBookedMeetingsBtwTimeSlots(ArrayList<String> employeeIds, LocalDateTime earliestTime,
                                                            LocalDateTime latestTime) throws EmployeeNotFoundException, Exception {

        ArrayList<Meeting> filterList = new ArrayList<>();
        ArrayList<Meeting> employeeData;
        for (String employeeId : employeeIds) {
            employeeData = fetchEmployeeData(employeeId);
            for (Meeting meeting : employeeData) {
                if ((meeting.getStartDatetimeMeeting().equals(earliestTime)
                        || meeting.getStartDatetimeMeeting().isAfter(earliestTime))
                        && ((meeting.getEndDateTimeMeeting().equals(latestTime)
                        || meeting.getEndDateTimeMeeting().isBefore(latestTime)))) {
                    filterList.add(meeting);
                }
            }
        }
        return filterList;
    }

    // Getting maximum possible time slots as per given dates, times and desired length of meeting and
    // deleting the meeting slots after comparing with already saved meeting date and times.
    public ArrayList<MeetingSlot> getPossibleMeetingSlots(ArrayList<Meeting> meetingList, LocalDateTime earliestStartTime,
                                                          LocalDateTime latestEndTime, Long meetingLength) {
        LocalDateTime startTime = earliestStartTime;
        LocalDateTime endTime = startTime.plusMinutes(meetingLength);
        ArrayList<MeetingSlot> meetingSlots = new ArrayList<>();
        while (endTime.isBefore(latestEndTime) || endTime.equals(latestEndTime)) {
            MeetingSlot meetingSlot = MeetingSlot.builder()
                    .startDateTime(startTime)
                    .endDateTime(endTime)
                    .build();
            meetingSlots.add(meetingSlot);
            startTime = endTime;
            endTime = endTime.plusMinutes(meetingLength);
        }

        ArrayList<MeetingSlot> slotsToBeDeleted = new ArrayList<>();
        for (int i = 0; i < meetingSlots.size(); i++) {
            MeetingSlot value = meetingSlots.get(i);
            for (int j = 0; j < meetingList.size(); j++) {
                Meeting createMeeting = meetingList.get(j);
                if (value.getStartDateTime().isBefore(createMeeting.getStartDatetimeMeeting()) &&
                        value.getEndDateTime().isAfter(createMeeting.getStartDatetimeMeeting()) &&
                        value.getEndDateTime().isBefore(createMeeting.getEndDateTimeMeeting())) {
                    slotsToBeDeleted.add(value);

                } else if (value.getStartDateTime().isAfter(createMeeting.getStartDatetimeMeeting())
                        && (value.getStartDateTime().isBefore(createMeeting.getEndDateTimeMeeting())
                        && (value.getEndDateTime().isAfter(createMeeting.getStartDatetimeMeeting()))
                        && (value.getEndDateTime().isBefore(createMeeting.getEndDateTimeMeeting())))) {
                    slotsToBeDeleted.add(value);

                } else if (value.getStartDateTime().isAfter(createMeeting.getStartDatetimeMeeting())
                        && (value.getStartDateTime().isBefore(createMeeting.getEndDateTimeMeeting()))) {
                    slotsToBeDeleted.add(value);

                } else if (value.getStartDateTime().isEqual(createMeeting.getStartDatetimeMeeting())
                        && (value.getEndDateTime().isBefore(createMeeting.getEndDateTimeMeeting()))) {
                    slotsToBeDeleted.add(value);

                } else if (value.getStartDateTime().isEqual(createMeeting.getStartDatetimeMeeting())
                        && (value.getEndDateTime().isEqual(createMeeting.getEndDateTimeMeeting()))) {
                    slotsToBeDeleted.add(value);

                } else if (value.getStartDateTime().isBefore(createMeeting.getStartDatetimeMeeting())
                        && (value.getEndDateTime().isEqual(createMeeting.getEndDateTimeMeeting()))) {
                    slotsToBeDeleted.add(value);
                }
                else if (value.getStartDateTime().isAfter(createMeeting.getStartDatetimeMeeting())
                        && (value.getEndDateTime().isEqual(createMeeting.getEndDateTimeMeeting()))) {
                    slotsToBeDeleted.add(value);
                }
            }
        }
        for (MeetingSlot toDeletedSlot : slotsToBeDeleted) {
            for (int meetingSlot = 0; meetingSlot < meetingSlots.size(); meetingSlot++) {
                if (toDeletedSlot.equals(meetingSlots.get(meetingSlot))) {
                    meetingSlots.remove(meetingSlots.get(meetingSlot));
                }
            }
        }
        return meetingSlots;
    }

    // Filtering the available time slots as per the office timing
    public ArrayList<MeetingSlot> officeHourFilter(ArrayList<MeetingSlot> slotsList, LocalTime startTime, LocalTime endTime) {
        ArrayList<MeetingSlot> finalSlotsList = new ArrayList<>();
        LocalTime startOfficeHour;
        LocalTime endOfficeHour;
        for (MeetingSlot meetingSlot : slotsList) {
            startOfficeHour = meetingSlot.getStartDateTime().toLocalTime();
            endOfficeHour = meetingSlot.getEndDateTime().toLocalTime();
            if ((startOfficeHour.equals(startTime) || startOfficeHour.isAfter(startTime)) &&
                    (endOfficeHour.equals(endTime) || endOfficeHour.isBefore(endTime) && endOfficeHour.isAfter(startOfficeHour))
                    ) {
                finalSlotsList.add(meetingSlot);
            }
        }
        return finalSlotsList;
    }

    // Display the final list of available time slots
    public ArrayList<MeetingSlot> displayAvailableSlots(ArrayList<String> employeeId, LocalDateTime earliestStartTime,
                                                        LocalDateTime latestEndTime, Long meetingLength,
                                                        LocalTime officeStartTime, LocalTime officeEndTime) throws Exception, EmployeeNotFoundException {
        ArrayList<Meeting> filterList = getBookedMeetingsBtwTimeSlots(employeeId, earliestStartTime, latestEndTime);
        ArrayList<MeetingSlot> meetingSlots = getPossibleMeetingSlots(filterList, earliestStartTime, latestEndTime, meetingLength);
        ArrayList<MeetingSlot> finalSlotsList = officeHourFilter(meetingSlots, officeStartTime, officeEndTime);

        if (finalSlotsList.size() > 0) {
            System.out.println("Below time slots are available for Meeting");
            for (MeetingSlot slot : finalSlotsList) {
                System.out.println(slot.getStartDateTime() + " - " + slot.getEndDateTime());
            }
        } else {
            System.out.println("No time slots available for Meeting");
        }
        return finalSlotsList;
    }

    // function for formatting the API dates and time into desired Date and time format.
    public LocalDateTime localDateTimeFormatter(String dateTime) {
        DateFormat existingFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        DateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formattedDate = null;
        Date date;
        String output;
        if (dateTime.length() > 18)
            try {
                date = existingFormat.parse(dateTime);
                output = desiredFormat.format(date);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                formattedDate = LocalDateTime.parse(output, dtf);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        return formattedDate;
    }
}

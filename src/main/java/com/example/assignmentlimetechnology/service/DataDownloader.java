package com.example.assignmentlimetechnology.service;

import com.example.assignmentlimetechnology.entity.CreateMeeting;
import com.example.assignmentlimetechnology.entity.MeetingSlot;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class DataDownloader {

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

    public ArrayList<CreateMeeting> readingData(String id) throws Exception {
        File file = new File("src\\main\\resources\\bookedMeetingFile.txt");
        Scanner sc = new Scanner(file);
        ArrayList<CreateMeeting> meetings = new ArrayList<>();
        String[] dataLine;
        while (sc.hasNextLine()) {
            dataLine = sc.nextLine().split(";");
            if (dataLine.length == 4 && dataLine[0].equals(id)) {
                LocalDateTime startMeetingDateTime = dateTimeFormat(dataLine[1]);
                LocalDateTime endMeetingDateTime = dateTimeFormat(dataLine[2]);
                if (startMeetingDateTime != null && endMeetingDateTime != null) {
                    CreateMeeting createMeeting = CreateMeeting.builder()
                            .employeeID(dataLine[0])
                            .startDatetimeMeeting(startMeetingDateTime)
                            .endDateTimeMeeting(endMeetingDateTime)
                            .build();
                    meetings.add(createMeeting);
                }
            }
        }
        return meetings;
    }

    public ArrayList<CreateMeeting> meetingsBtwTimeSlots(ArrayList<String> employeeIds, LocalDateTime earliestTime,
                                                         LocalDateTime latestTime) throws Exception {
        ArrayList<CreateMeeting> filterList = new ArrayList<>();
        ArrayList<CreateMeeting> employeeData;
        for(String employeeId: employeeIds){
            employeeData = readingData(employeeId);
            for (CreateMeeting meeting:employeeData) {
                if((meeting.getStartDatetimeMeeting().equals(earliestTime)
                        || meeting.getStartDatetimeMeeting().isAfter(earliestTime))
                        && ((meeting.getEndDateTimeMeeting().equals(latestTime)
                        || meeting.getEndDateTimeMeeting().isBefore(latestTime)))){
                    filterList.add(meeting);
                }
                else if (meeting.getEndDateTimeMeeting().isAfter(earliestTime)){
                    filterList.add(meeting);
                }
            }
        }
        return filterList;
    }

    public ArrayList<MeetingSlot> findingSlots(ArrayList<CreateMeeting> meetingList, LocalDateTime earliestStartTime, LocalDateTime latestEndTime, Long meetingLength) {
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
        for (MeetingSlot value : meetingSlots) {
            for (CreateMeeting createMeeting : meetingList) {
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
                }
            }
        }
        for (MeetingSlot value : slotsToBeDeleted) {
            for (int meetingSlot = 0; meetingSlot < meetingSlots.size(); meetingSlot++) {
                if (value.equals(meetingSlots.get(meetingSlot))) {
                    meetingSlots.remove(meetingSlots.get(meetingSlot));
                }
            }
        }
        return meetingSlots;
    }

    public ArrayList<MeetingSlot> displayAvailableSlots(ArrayList<String> employeeId, LocalDateTime earliestStartTime,
                                      LocalDateTime latestEndTime, Long meetingLength,
                                      LocalTime officeStartTime, LocalTime officeEndTime) throws Exception {
        ArrayList<CreateMeeting> filterList = meetingsBtwTimeSlots(employeeId, earliestStartTime, latestEndTime);
        ArrayList<MeetingSlot> meetingSlots = findingSlots(filterList, earliestStartTime, latestEndTime, meetingLength);
        ArrayList<MeetingSlot> finalSlotsList = officeHourCal(meetingSlots, officeStartTime, officeEndTime);
        System.out.println(filterList);
        System.out.println(meetingSlots);
        System.out.println(finalSlotsList);
        if (finalSlotsList.size() > 0 ){
            System.out.println("Below time are available for Meeting" );
            for (MeetingSlot slot : finalSlotsList ) {
                System.out.println(slot.getStartDateTime() +" - "+slot.getEndDateTime());
            }
        }else {
            System.out.println("No slots available for Meeting" );
        }
        return finalSlotsList;
    }

    public LocalDateTime dateTimeFormat(String dateTime) {
        DateFormat existingFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        DateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formattedDate = null;
        Date date;
        String output;
        if (dateTime.length() > 18)
            try {
                date = existingFormat.parse(dateTime);
                output = desiredFormat.format(date);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
                formattedDate = LocalDateTime.parse(output, dtf);
            } catch (ParseException pe) {
                pe.printStackTrace();
            }
        return formattedDate;
    }

    public ArrayList<MeetingSlot> officeHourCal(ArrayList<MeetingSlot> slotsList, LocalTime startTime, LocalTime endTime) {
        ArrayList<MeetingSlot> finalSlotsList = new ArrayList<>();
        LocalTime startOfficeHour;
        LocalTime endOfficeHour;
        for (MeetingSlot meetingSlot : slotsList) {
            startOfficeHour = meetingSlot.getStartDateTime().toLocalTime();
            endOfficeHour = meetingSlot.getEndDateTime().toLocalTime();
            if ((startOfficeHour.equals(startTime) || startOfficeHour.isAfter(startTime)) &&
                    (endOfficeHour.equals(endTime) || endOfficeHour.isBefore(endTime) && endOfficeHour.isAfter(startOfficeHour))) {
                finalSlotsList.add(meetingSlot);
            }
        }
        return finalSlotsList;
    }
}

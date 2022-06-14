package com.example.assignmentlimetechnology.service;

import com.example.assignmentlimetechnology.entity.CreateMeeting;
import com.example.assignmentlimetechnology.entity.Meeting;
import com.example.assignmentlimetechnology.repository.MeetingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class DataDownloader {
    @Autowired
    private MeetingRepository meetingRepository;
    public void downloadData(String apiUrl, String file) throws Exception {
        URL url = new URL(apiUrl);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count = 0;
        while ((count = bis.read(buffer, 0, 1024)) != -1) {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }
    public void readingData(String id) throws Exception {
        File file = new File("src\\main\\resources\\bookedMeetingFile.txt");
        Scanner sc = new Scanner(file);
        ArrayList<CreateMeeting> meetings = new ArrayList<>();
        String[] dataLine;
        int count = 0;
        while (sc.hasNextLine()) {
            count+=1;
            dataLine = sc.nextLine().split(";");
            if (dataLine.length == 4 && dataLine[0].equals(id) ) {
                //System.out.println(dataLine[1]);
                if(dataLine[0].equals(id)){
                    String startMeetingDateTimeStr = dateTimeFormat(dataLine[1]);
                    String endMeetingDateTimeStr = dateTimeFormat(dataLine[2]);
                    if (startMeetingDateTimeStr !=null && endMeetingDateTimeStr != null){
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
                        LocalDateTime startMeetingDateTime = LocalDateTime.parse(startMeetingDateTimeStr, dtf);
                        LocalDateTime endMeetingDateTime = LocalDateTime.parse(endMeetingDateTimeStr, dtf);
                        CreateMeeting createMeeting = CreateMeeting.builder()
                                .employeeID(dataLine[0])
                                .startDatetimeMeeting(startMeetingDateTime)
                                .endDateTimeMeeting(endMeetingDateTime)
                                .build();
                        meetings.add(createMeeting);
                    }
                }

            }
        }
        System.out.println(meetings);
        System.out.println(meetings.size());
        System.out.println("Count"+ count);
    }

    public String dateTimeFormat(String dateTime){
        String input = dateTime;
        DateFormat existingFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        DateFormat desiredFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        String output =null;
        if (input.length() > 18)
            try{
            date= existingFormat.parse(input);
            output = desiredFormat.format(date);
            }catch(ParseException pe){
                pe.printStackTrace();
                }
        return output;
    }

}

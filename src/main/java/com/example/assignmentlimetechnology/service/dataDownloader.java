package com.example.assignmentlimetechnology.service;

import com.example.assignmentlimetechnology.entity.Meeting;
import com.example.assignmentlimetechnology.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Scanner;

@Service
public class dataDownloader {
    @Autowired
    MeetingRepository meetingRepository;

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
    public void readingData() throws Exception {
        File file = new File("src\\main\\resources\\bookedMeetingFile.txt");
        Scanner sc = new Scanner(file);
        String[] dataLine;
        while (sc.hasNextLine()) {
            dataLine = sc.nextLine().split(";");
            if(dataLine.length == 4) {
                System.out.println(dataLine[1]);
                Meeting meeting = Meeting.builder()
                        .id(dataLine[3])
                        .meetingStartDateTime(dataLine[1])
                        .meetingEndDateTime(dataLine[2])
                        .employeeId(dataLine[0])
                        .build();
                meetingRepository.save(meeting);
            }
        }
    }
}

package com.example.assignmentlimetechnology;

import com.example.assignmentlimetechnology.service.dataDownloader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssignmentLimeTechnologyApplication {

    public static void main(String[] args) throws Exception {

        dataDownloader dataDownloader = new dataDownloader();
        SpringApplication.run(AssignmentLimeTechnologyApplication.class, args);
        String url = "https://builds.lundalogik.com/api/v1/builds/freebusy/versions/1.0.0/file";
        String fileLocation = "src\\main\\resources\\bookedMeetingFile.txt";
        try{
            dataDownloader.downloadData(url, fileLocation);
            dataDownloader.readingData();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

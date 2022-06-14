package com.example.assignmentlimetechnology;

import com.example.assignmentlimetechnology.service.DataDownloader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class AssignmentLimeTechnologyApplication {

    public static void main(String[] args) throws Exception {
        DataDownloader dataDownloader = new DataDownloader();

        SpringApplication.run(AssignmentLimeTechnologyApplication.class, args);
        String url = "https://builds.lundalogik.com/api/v1/builds/freebusy/versions/1.0.0/file";
        String fileLocation = "src\\main\\resources\\bookedMeetingFile.txt";
        try {
            dataDownloader.downloadData(url, fileLocation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            Scanner input = new Scanner(System.in);
            String employeeId = input.nextLine();
            dataDownloader.readingData(employeeId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}

package com.example.assignmentlimetechnology;

import com.example.assignmentlimetechnology.exception.EmployeeNotFoundException;
import com.example.assignmentlimetechnology.service.DataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

@SpringBootApplication
public class AssignmentLimeTechnologyApplication {

    public static void main(String[] args) {

        SpringApplication.run(AssignmentLimeTechnologyApplication.class, args);

        DataService dataDownloader = new DataService();
        String url = "https://builds.lundalogik.com/api/v1/builds/freebusy/versions/1.0.0/file";
        String fileLocation = "src\\main\\resources\\bookedMeetingFile.txt";
        try {
            dataDownloader.downloadData(url, fileLocation);
        }catch (Exception e) {
            e.printStackTrace();
        }
        try{
            Scanner input = new Scanner(System.in);

            System.out.println("Please enter number of employees for a meeting");
            String employeeNum = input.nextLine();
            int employeesNumber = Integer.parseInt(employeeNum);
            ArrayList<String> employeeIds = new ArrayList<>();
            for(int number = 0; number < employeesNumber; number++){
                System.out.println("Please enter employee id");
                String employeeId = input.nextLine();
                employeeIds.add(employeeId);
            }

            System.out.println("Please enter desired meeting length (minutes)");
            String meetingLength = input.nextLine();

            Long meetingLengthLong = Long.parseLong(meetingLength);
            System.out.println("Please enter desired start time and date. Input format should " +
                    "be (uuuu-MM-dd HH:mm:ss) Ex.3/19/2015 10:00:00 AM");

            String meetingStartTime= input.nextLine();
            System.out.println("Please enter desired end time and date.Input format " +
                    "should be (uuuu-MM-dd HH:mm:ss) Ex.3/19/2015 14:00:00 PM");

            String meetingEndTime= input.nextLine();
            System.out.println("Please enter office (HH.mm) Ex: 08.00 ");
            String officeHoursStart = input.nextLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
            LocalTime officeStartTime = LocalTime.parse(officeHoursStart, formatter);

            System.out.println("Please enter office (HH.mm) Ex: 17.00 ");
            String officeHoursEnd = input.nextLine();
            LocalTime officeEndTime = LocalTime.parse(officeHoursEnd, formatter);

            LocalDateTime startMeetingDateTime = dataDownloader.localDateTimeFormatter(meetingStartTime);
            LocalDateTime endMeetingDateTime = dataDownloader.localDateTimeFormatter(meetingEndTime);
            dataDownloader.displayAvailableSlots(employeeIds,startMeetingDateTime, endMeetingDateTime,
                    meetingLengthLong, officeStartTime, officeEndTime);

        }catch (Exception e){
            System.out.println("Wrong Input format");
            e.printStackTrace();
        } catch (EmployeeNotFoundException e) {
            e.printStackTrace();
        }
    }
}

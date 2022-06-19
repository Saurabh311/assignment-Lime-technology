package com.example.assignmentlimetechnology;

import com.example.assignmentlimetechnology.service.RunApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class AssignmentLimeTechnologyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssignmentLimeTechnologyApplication.class, args);
        RunApp startApp = new RunApp();
        startApp.startAPP();
    }
}

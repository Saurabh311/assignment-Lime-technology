package com.example.assignmentlimetechnology;

import com.example.assignmentlimetechnology.Exception.EmployeeNotFoundException;
import com.example.assignmentlimetechnology.entity.CreateMeeting;
import com.example.assignmentlimetechnology.service.DataDownloader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
        ArrayList<CreateMeeting> employeeData = dataDownloader.fetchEmployeeData("325936446199886750546547952200356331252");
        Assertions.assertNotNull(employeeData);
        assertEquals(64, employeeData.size());
    }

    @Test
    public void test_fetch_employee_data_with_wrong_id() throws Exception, EmployeeNotFoundException {
        DataDownloader dataDownloader = new DataDownloader();
        Assertions.assertThrows(EmployeeNotFoundException.class, () -> {
            ArrayList<CreateMeeting> employeeData = dataDownloader.fetchEmployeeData("wrongID");
        });
    }





}

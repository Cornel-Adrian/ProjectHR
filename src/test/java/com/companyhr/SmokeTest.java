package com.companyhr;

import static org.assertj.core.api.Assertions.assertThat;

import com.companyhr.web.controller.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {

    @Autowired
    private LoginPageController login_controller;
    @Autowired
    private AddHolidayHrPageController addholiday_controller;
    @Autowired
    private CalendarPageController calendar_controller;
    @Autowired
    private EmployeePageController employee_controller;
    @Autowired
    private ErrorPageController error_controller;
    @Autowired
    private FileDownloadController file_controller;
    @Autowired
    private UserHomePageControler user_controller;


    @Test
    public void contexLoads() throws Exception {
        assertThat(login_controller).isNotNull();
        assertThat(addholiday_controller).isNotNull();
        assertThat(calendar_controller).isNotNull();
        assertThat(employee_controller).isNotNull();
        assertThat(error_controller).isNotNull();
        assertThat(file_controller).isNotNull();
        assertThat(user_controller).isNotNull();
    }

}

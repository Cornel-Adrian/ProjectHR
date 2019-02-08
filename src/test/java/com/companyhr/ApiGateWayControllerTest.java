package com.companyhr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.companyhr.model.DaysOff;
import com.companyhr.repository.DaysOffRepository;
import com.companyhr.repository.EmployeeRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = Application.class)
@AutoConfigureMockMvc
// @TestPropertySource(locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
public class ApiGateWayControllerTest{
    @Autowired
    private MockMvc mvc;

    @Autowired
    private DaysOffRepository repository;

    @After
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void FetchRightDaysOff() throws Exception {
        createTestDaysOff(1L,2L,"asa am vrut",1L);
        createTestDaysOff(2L,2L,"am ales sa fac asta",2L);

        // @formatter:off
        mvc.perform(get("/api/getalldaysoff").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].employeeId", is(2)))
                .andExpect(jsonPath("$[0].reasonLeave", is("asa am vrut")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].employeeId", is(2)))
                .andExpect(jsonPath("$[1].reasonLeave", is("am ales sa fac asta")));
        // @formatter:on
    }

    private void createTestDaysOff(Long id,Long emId, String reason, Long status) {
        DaysOff dor = new DaysOff();
        dor.setId(id);
        dor.setEmployeeId(emId);
        dor.setReasonLeave(reason);
        dor.setStatus(status);
        dor.setEndDate(new Date());
        dor.setStartDate(new Date());
        repository.saveAndFlush(dor);
    }




}
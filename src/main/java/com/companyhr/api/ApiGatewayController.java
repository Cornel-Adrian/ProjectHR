package com.companyhr.api;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.companyhr.model.DaysOff;
import com.companyhr.model.EmployeeCredentials;
import com.companyhr.repository.DaysOffRepository;
import com.companyhr.repository.EmployeeCredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * The Api gateway controller.
 */
@RestController
@RequestMapping("/api/")
public class ApiGatewayController {

    /**
     * The Days off repository.
     */
    @Autowired
    DaysOffRepository daysOffRepository;

    /**
     * The Employee credentials repository.
     */
    @Autowired
    EmployeeCredentialsRepository employeeCredentialsRepository;

    /**
     * Get all days off method.
     *
     * @return the list of days off
     */
    @RequestMapping("getalldaysoff")
    public List<DaysOff> getalldaysoff() {

        List<DaysOff> listOfdays=daysOffRepository.findAll();

        return listOfdays;
    }

    /**
     * Gets customer by id.
     *
     * @param employeeId the employee id
     * @return the customer by id
     */
    @RequestMapping(value = "getdaysoff/{employeeId}", method = RequestMethod.GET)
    public DaysOff getCustomerById(@PathVariable final Long employeeId) {

        List<EmployeeCredentials> credy=employeeCredentialsRepository.findAll();
        List<DaysOff> listy=daysOffRepository.findAll();


        DaysOff returny= listy.stream().
                filter(returning-> employeeId.
                        equals((returning.getEmployeeId()))).
                findFirst().orElse(null);

        return returny;
    }
}



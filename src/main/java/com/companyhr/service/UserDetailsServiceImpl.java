package com.companyhr.service;

import com.companyhr.model.Employee;
import com.companyhr.model.EmployeeCredentials;
import com.companyhr.model.Job;
import com.companyhr.repository.EmployeeCredentialsRepository;
import com.companyhr.repository.EmployeeRepository;
import com.companyhr.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private EmployeeCredentialsRepository employeeCredentialsRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        EmployeeCredentials employeeCredentials = employeeCredentialsRepository.findByUsername(username);
        Optional<Employee> employee = employeeRepository.findById(employeeCredentials.getId());
        if (employee.isPresent()) {
            Employee employee1 = employee.get();
            Optional<Job> jobOptional = jobRepository.findById(employee1.getJobId());
            if (jobOptional.isPresent()) {
                Job job = jobOptional.get();
                grantedAuthorities.add(new SimpleGrantedAuthority(job.getName()));
            }
        }
        return new org.springframework.security.core.userdetails.User
                (employeeCredentials.getUsername(), employeeCredentials.getPassword(), grantedAuthorities);
    }
}

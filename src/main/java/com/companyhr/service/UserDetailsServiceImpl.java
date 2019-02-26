package com.companyhr.service;

import com.companyhr.model.EmployeeCredentials;
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
import java.util.Set;

/**
 * Contains methods for user role setting
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private EmployeeCredentialsRepository employeeCredentialsRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Setting the authority for a user
     *
     * @param username the username of the current logged in user
     * @return user details with authority setting
     * @throws UsernameNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        EmployeeCredentials employeeCredentials = employeeCredentialsRepository.findByUsername(username);
        if (employeeCredentials.getJobId() == null) {
            throw new UsernameNotFoundException("You are a stranger!");

        }
        if (employeeCredentials.getJobId().toString().equals("1")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if (employeeCredentials.getJobId().toString().equals("2")) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_HR"));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_SIMPLEUSER"));
        }
        return new org.springframework.security.core.userdetails.User
                (employeeCredentials.getUsername(), employeeCredentials.getPassword(), grantedAuthorities);
    }
}

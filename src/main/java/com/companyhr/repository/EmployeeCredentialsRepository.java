package com.companyhr.repository;

import com.companyhr.model.EmployeeCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * class contains method for db employee credentials
 */
@Repository
public interface EmployeeCredentialsRepository extends JpaRepository<EmployeeCredentials, Long> {
    /**
     * find a user by user name
     *
     * @param username the username of the user
     * @return employee credentials
     */
    EmployeeCredentials findByUsername(String username);

    EmployeeCredentials findByEmployeeId(Long employeeId);
}

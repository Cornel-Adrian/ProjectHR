package com.companyhr.repository;

import com.companyhr.model.EmployeeCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeCredentialsRepository extends JpaRepository<EmployeeCredentials, Long> {

   EmployeeCredentials findByUsername(String username);

}

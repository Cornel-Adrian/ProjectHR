package com.companyhr.repository;

import com.companyhr.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * class contains method for db employee
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


}

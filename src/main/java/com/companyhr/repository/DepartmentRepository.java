package com.companyhr.repository;

import com.companyhr.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * class contains method for db department
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}

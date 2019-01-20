package com.companyhr.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.companyhr.api.model.*;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}

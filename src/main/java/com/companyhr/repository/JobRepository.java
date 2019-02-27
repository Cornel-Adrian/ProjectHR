package com.companyhr.repository;

import com.companyhr.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * class contains method for db job
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

}

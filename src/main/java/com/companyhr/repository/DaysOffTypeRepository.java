package com.companyhr.repository;


import com.companyhr.model.DaysOffType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * class contains method for db the type of the days off
 */
@Repository
public interface DaysOffTypeRepository extends JpaRepository<DaysOffType, Long> {

}

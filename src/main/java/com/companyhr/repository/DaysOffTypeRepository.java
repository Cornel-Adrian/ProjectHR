package com.companyhr.repository;


import com.companyhr.model.DaysOffType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaysOffTypeRepository extends JpaRepository<DaysOffType, Long> {

}

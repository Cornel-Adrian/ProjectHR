package com.companyhr.repository;

import com.companyhr.model.DaysOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaysOffRepository extends JpaRepository<DaysOff, Long> {

}

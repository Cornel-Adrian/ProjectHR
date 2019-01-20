package com.companyhr.api.repository;

import com.companyhr.api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaysOffRepository extends JpaRepository<DaysOff, Long> {

}
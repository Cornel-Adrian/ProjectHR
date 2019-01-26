package com.companyhr.api.repository;

import com.companyhr.api.model.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {

    List<Date> findByStartDateBetween(Date startDate, Date endDate);
}

package com.companyhr.repository;


import com.companyhr.model.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * class contains method for db for public holiday
 */
@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {

    /**
     * @param startDate the first day of the holiday requested
     * @param endDate   the last day of the holiday requested
     * @return the period of the holiday requested
     */
    List<Date> findByStartDateBetween(Date startDate, Date endDate);
}

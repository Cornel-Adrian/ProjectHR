package com.companyhr.repository;


import com.companyhr.model.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {

    //List<Date> findByStartDateBetween(Date startDate, Date endDate);
}

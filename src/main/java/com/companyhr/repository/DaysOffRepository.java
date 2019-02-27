package com.companyhr.repository;

import com.companyhr.model.DaysOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * class contains method for db days off
 */
@Repository
public interface DaysOffRepository extends JpaRepository<DaysOff, Long> {

    /**
     * a list with all days off
     *
     * @param id the id of the user
     * @return the days off
     */
    List<DaysOff> findByemployeeId(long id);

    List<DaysOff> findAllByemployeeId(long id);
}

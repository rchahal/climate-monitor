package com.expbank.climatemonitor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ClimateInfoRepository extends CrudRepository<ClimateInfo, Long> {

    // TODO:  There has to be a way to do this with a single method but I'm out of time.

    @Query("select ci from ClimateInfo ci where ci.date>:startDate and ci.date<:endDate")
    List<ClimateInfo> findByDateRange(@Param("startDate") Date startDate,
                                   @Param("endDate") Date endDate);

    @Query("select ci from ClimateInfo ci where ci.date>:startDate")
    List<ClimateInfo> findAfterStartDate(@Param("startDate") Date startDate);

    @Query("select ci from ClimateInfo ci where ci.date<:endDate")
    List<ClimateInfo> findBeforeEndDate(@Param("endDate") Date endDate);

}

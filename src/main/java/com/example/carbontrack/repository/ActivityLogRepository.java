package com.example.carbontrack.repository;

import com.example.carbontrack.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findByHouseholdIdOrderByActivityDateDesc(Long householdId);
    List<ActivityLog> findByActivityDateBetween(LocalDate from, LocalDate to);
    List<ActivityLog> findTop10ByOrderByActivityDateDesc();
    @Query("select coalesce(sum(a.calculatedEmission), 0) from ActivityLog a where a.household.id = :householdId and a.activityDate between :from and :to")
    double sumEmissions(@Param("householdId") Long householdId, @Param("from") LocalDate from, @Param("to") LocalDate to);
    @Query("select coalesce(sum(a.calculatedEmission), 0) from ActivityLog a where a.activityDate between :from and :to")
    double sumAllEmissions(@Param("from") LocalDate from, @Param("to") LocalDate to);
    @Query("select a from ActivityLog a where (:householdId is null or a.household.id = :householdId) and (:type is null or a.activityType = :type) and a.activityDate between :from and :to order by a.activityDate desc")
    List<ActivityLog> filter(@Param("householdId") Long householdId, @Param("type") ActivityType type, @Param("from") LocalDate from, @Param("to") LocalDate to);
}

package com.example.carbontrack.repository;

import com.example.carbontrack.entity.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface HouseholdRepository extends JpaRepository<Household, Long> {
    List<Household> findByHouseholdNameContainingIgnoreCaseOrOwnerNameContainingIgnoreCase(String name, String owner);
    @Query("select count(h) from Household h") long countHouseholds();
}

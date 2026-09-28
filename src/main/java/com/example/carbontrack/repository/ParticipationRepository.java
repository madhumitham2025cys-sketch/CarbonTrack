package com.example.carbontrack.repository;

import com.example.carbontrack.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Optional<Participation> findByHouseholdIdAndChallengeId(Long householdId, Long challengeId);
    List<Participation> findByHouseholdId(Long householdId);
    List<Participation> findByChallengeId(Long challengeId);
    List<Participation> findByProgressStatus(ProgressStatus status);
    List<Participation> findTop5ByOrderByReductionPercentageDesc();
}

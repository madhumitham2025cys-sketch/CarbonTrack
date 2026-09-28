package com.example.carbontrack.repository;

import com.example.carbontrack.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByStatus(ChallengeStatus status);
}

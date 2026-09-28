package com.example.carbontrack.service;

import com.example.carbontrack.entity.*;
import com.example.carbontrack.exception.*;
import com.example.carbontrack.repository.ChallengeRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChallengeService {
    private final ChallengeRepository repository;
    public ChallengeService(ChallengeRepository repository) { this.repository = repository; }
    public List<Challenge> findAll(ChallengeStatus status) { return status == null ? repository.findAll() : repository.findByStatus(status); }
    public List<Challenge> active() { return repository.findByStatus(ChallengeStatus.ACTIVE); }
    public Challenge findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Challenge not found")); }
    public Challenge save(Challenge challenge) {
        if (challenge.getStartDate() != null && challenge.getEndDate() != null && !challenge.getEndDate().isAfter(challenge.getStartDate())) {
            throw new InvalidChallengeException("End date must be after start date");
        }
        if (challenge.getTargetReductionPercentage() < 1 || challenge.getTargetReductionPercentage() > 100) {
            throw new InvalidChallengeException("Target reduction must be between 1 and 100");
        }
        return repository.save(challenge);
    }
    public void delete(Long id) { repository.delete(findById(id)); }
    public long countActive() { return active().size(); }
}

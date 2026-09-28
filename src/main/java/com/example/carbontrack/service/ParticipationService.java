package com.example.carbontrack.service;

import com.example.carbontrack.entity.*;
import com.example.carbontrack.exception.*;
import com.example.carbontrack.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class ParticipationService {
    private final ParticipationRepository repository; private final HouseholdRepository households; private final ChallengeRepository challenges; private final CarbonFootprintService footprint;
    public ParticipationService(ParticipationRepository repository, HouseholdRepository households, ChallengeRepository challenges, CarbonFootprintService footprint) { this.repository = repository; this.households = households; this.challenges = challenges; this.footprint = footprint; }
    public List<Participation> findAll() { return repository.findAll(); }
    public Participation findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Participation not found")); }
    public Participation save(Participation p, Long householdId, Long challengeId) { Household h = households.findById(householdId).orElseThrow(() -> new ResourceNotFoundException("Household not found")); Challenge c = challenges.findById(challengeId).orElseThrow(() -> new ResourceNotFoundException("Challenge not found")); if (c.getEndDate().isBefore(LocalDate.now())) throw new InvalidChallengeException("A household cannot join an ended challenge"); if (p.getId() == null && repository.findByHouseholdIdAndChallengeId(householdId, challengeId).isPresent()) throw new DuplicateParticipationException("Household already joined this challenge"); YearMonth current = YearMonth.now(); YearMonth previous = current.minusMonths(1); p.setHousehold(h); p.setChallenge(c); p.setBaselineFootprint(footprint.calculateMonthlyFootprint(h.getId(), previous.getYear(), previous.getMonthValue())); p.setCurrentFootprint(footprint.calculateMonthlyFootprint(h.getId(), current.getYear(), current.getMonthValue())); p.setReductionPercentage(footprint.calculateReduction(p.getBaselineFootprint(), p.getCurrentFootprint())); p.setProgressStatus(p.getReductionPercentage() >= c.getTargetReductionPercentage() ? ProgressStatus.TARGET_ACHIEVED : p.getReductionPercentage() > 0 ? ProgressStatus.IN_PROGRESS : ProgressStatus.NOT_STARTED); return repository.save(p); }
    public void delete(Long id) { repository.delete(findById(id)); }
    public long count() { return repository.count(); }
    public List<Participation> topFive() { return repository.findTop5ByOrderByReductionPercentageDesc(); }
}

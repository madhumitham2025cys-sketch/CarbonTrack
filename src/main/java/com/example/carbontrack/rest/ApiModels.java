package com.example.carbontrack.rest;

import com.example.carbontrack.entity.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public final class ApiModels {
    private ApiModels() { }

    public record HouseholdResponse(Long id, String householdName, String ownerName, String email, String location, int numberOfMembers, LocalDateTime createdAt) {
        static HouseholdResponse from(Household h) { return new HouseholdResponse(h.getId(), h.getHouseholdName(), h.getOwnerName(), h.getEmail(), h.getLocation(), h.getNumberOfMembers(), h.getCreatedAt()); }
    }
    public record ActivityLogResponse(Long id, Long householdId, String householdName, ActivityType activityType, double quantity, String unit, LocalDate activityDate, double emissionFactor, double calculatedEmission, String notes) {
        static ActivityLogResponse from(ActivityLog a) { return new ActivityLogResponse(a.getId(), a.getHousehold().getId(), a.getHousehold().getHouseholdName(), a.getActivityType(), a.getQuantity(), a.getUnit(), a.getActivityDate(), a.getEmissionFactor(), a.getCalculatedEmission(), a.getNotes()); }
    }
    public record ChallengeResponse(Long id, String challengeName, String description, double targetReductionPercentage, LocalDate startDate, LocalDate endDate, ChallengeStatus status) {
        static ChallengeResponse from(Challenge c) { return new ChallengeResponse(c.getId(), c.getChallengeName(), c.getDescription(), c.getTargetReductionPercentage(), c.getStartDate(), c.getEndDate(), c.getStatus()); }
    }
    public record ParticipationResponse(Long id, Long householdId, String householdName, Long challengeId, String challengeName, LocalDateTime joinedAt, double baselineFootprint, double currentFootprint, double reductionPercentage, ProgressStatus progressStatus) {
        static ParticipationResponse from(Participation p) { return new ParticipationResponse(p.getId(), p.getHousehold().getId(), p.getHousehold().getHouseholdName(), p.getChallenge().getId(), p.getChallenge().getChallengeName(), p.getJoinedAt(), p.getBaselineFootprint(), p.getCurrentFootprint(), p.getReductionPercentage(), p.getProgressStatus()); }
    }
    public record LeaderboardResponse(int rank, String householdName, double previousMonthFootprint, double currentMonthFootprint, double reductionPercentage) { }
}

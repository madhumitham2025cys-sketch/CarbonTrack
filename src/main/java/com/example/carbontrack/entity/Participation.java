package com.example.carbontrack.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"household_id", "challenge_id"}))
public class Participation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "household_id") private Household household;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "challenge_id") private Challenge challenge;
    private LocalDateTime joinedAt;
    private double baselineFootprint;
    private double currentFootprint;
    private double reductionPercentage;
    @Enumerated(EnumType.STRING) private ProgressStatus progressStatus;

    @PrePersist void defaults() {
        if (joinedAt == null) {
            joinedAt = LocalDateTime.now();
        }
        if (progressStatus == null) {
            progressStatus = ProgressStatus.NOT_STARTED;
        }
    }
    public Long getId() { return id; }
    public void setId(Long value) { id = value; }
    public Household getHousehold() { return household; }
    public void setHousehold(Household value) { household = value; }
    public Challenge getChallenge() { return challenge; }
    public void setChallenge(Challenge value) { challenge = value; }
    public LocalDateTime getJoinedAt() { return joinedAt; }
    public double getBaselineFootprint() { return baselineFootprint; }
    public void setBaselineFootprint(double value) { baselineFootprint = value; }
    public double getCurrentFootprint() { return currentFootprint; }
    public void setCurrentFootprint(double value) { currentFootprint = value; }
    public double getReductionPercentage() { return reductionPercentage; }
    public void setReductionPercentage(double value) { reductionPercentage = value; }
    public ProgressStatus getProgressStatus() { return progressStatus; }
    public void setProgressStatus(ProgressStatus value) { progressStatus = value; }
}

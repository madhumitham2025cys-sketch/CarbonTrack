package com.example.carbontrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Challenge {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String challengeName;
    private String description;
    @DecimalMin("1.0") @DecimalMax("100.0") private double targetReductionPercentage;
    @NotNull private LocalDate startDate;
    @NotNull private LocalDate endDate;
    @Enumerated(EnumType.STRING) private ChallengeStatus status;
    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true) private List<Participation> participations = new ArrayList<>();

    @PrePersist @PreUpdate void updateStatus() {
        LocalDate today = LocalDate.now();
        if (today.isBefore(startDate)) status = ChallengeStatus.UPCOMING;
        else if (today.isAfter(endDate)) status = ChallengeStatus.COMPLETED;
        else status = ChallengeStatus.ACTIVE;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getChallengeName() { return challengeName; }
    public void setChallengeName(String value) { challengeName = value; }
    public String getDescription() { return description; }
    public void setDescription(String value) { description = value; }
    public double getTargetReductionPercentage() { return targetReductionPercentage; }
    public void setTargetReductionPercentage(double value) { targetReductionPercentage = value; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate value) { startDate = value; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate value) { endDate = value; }
    public ChallengeStatus getStatus() { return status; }
    public List<Participation> getParticipations() { return participations; }
}

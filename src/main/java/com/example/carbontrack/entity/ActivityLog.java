package com.example.carbontrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
public class ActivityLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "household_id")
    private Household household;
    @Enumerated(EnumType.STRING) @NotNull private ActivityType activityType;
    @Positive private double quantity;
    @NotNull private String unit;
    @PastOrPresent @NotNull private LocalDate activityDate;
    private double emissionFactor;
    private double calculatedEmission;
    private String notes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Household getHousehold() { return household; }
    public void setHousehold(Household household) { this.household = household; }
    public ActivityType getActivityType() { return activityType; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }
    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public LocalDate getActivityDate() { return activityDate; }
    public void setActivityDate(LocalDate activityDate) { this.activityDate = activityDate; }
    public double getEmissionFactor() { return emissionFactor; }
    public double getCalculatedEmission() { return calculatedEmission; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setEmissionValues(double factor) { this.emissionFactor = factor; this.calculatedEmission = quantity * factor; }
}

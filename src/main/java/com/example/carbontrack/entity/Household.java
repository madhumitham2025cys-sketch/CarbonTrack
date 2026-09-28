package com.example.carbontrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Household {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank private String householdName;
    @NotBlank private String ownerName;
    @Email @NotBlank private String email;
    private String location;
    @Positive private int numberOfMembers;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActivityLog> activityLogs = new ArrayList<>();
    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participation> participations = new ArrayList<>();

    @PrePersist void onCreate() { if (createdAt == null) createdAt = LocalDateTime.now(); }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getHouseholdName() { return householdName; }
    public void setHouseholdName(String householdName) { this.householdName = householdName; }
    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public int getNumberOfMembers() { return numberOfMembers; }
    public void setNumberOfMembers(int numberOfMembers) { this.numberOfMembers = numberOfMembers; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<ActivityLog> getActivityLogs() { return activityLogs; }
    public List<Participation> getParticipations() { return participations; }
}

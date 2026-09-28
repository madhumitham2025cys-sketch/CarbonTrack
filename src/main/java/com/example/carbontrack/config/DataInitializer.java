package com.example.carbontrack.config;

import com.example.carbontrack.entity.*;
import com.example.carbontrack.repository.*;
import com.example.carbontrack.service.CarbonFootprintService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean CommandLineRunner seed(HouseholdRepository households, ActivityLogRepository logs, ChallengeRepository challenges, ParticipationRepository participations, CarbonFootprintService footprint) {
        return args -> {
            if (households.count() > 0) return;
            Household h1 = household("Green Home", "Asha Patel", "asha@example.com", "Pune", 4); Household h2 = household("Eco Family", "Ravi Kumar", "ravi@example.com", "Bengaluru", 3); Household h3 = household("Smart House", "Maya Singh", "maya@example.com", "Delhi", 2); Household h4 = household("Low Carbon Nest", "Noah Das", "noah@example.com", "Kochi", 5); Household h5 = household("The Renewables", "Sara Roy", "sara@example.com", "Mumbai", 3);
            households.saveAll(List.of(h1, h2, h3, h4, h5));
            for (Household h : List.of(h1, h2, h3, h4, h5)) { log(logs, h, ActivityType.ELECTRICITY, 80, "kWh", 1); log(logs, h, ActivityType.CAR_TRAVEL, 120, "km", 2); }
            Challenge c1 = challenge("January Reduction Sprint", "Reduce your footprint this month", 15, LocalDate.now().minusDays(10), LocalDate.now().plusDays(30)); Challenge c2 = challenge("Clean Commute", "Choose low carbon travel", 20, LocalDate.now().minusDays(5), LocalDate.now().plusDays(45)); Challenge c3 = challenge("Waste Less", "Reduce household waste", 10, LocalDate.now().plusDays(2), LocalDate.now().plusDays(60)); challenges.saveAll(List.of(c1, c2, c3));
            join(participations, h1, c1); join(participations, h2, c1); join(participations, h3, c2); join(participations, h4, c2); join(participations, h5, c1);
        };
    }
    private Household household(String name, String owner, String email, String location, int members) { Household h = new Household(); h.setHouseholdName(name); h.setOwnerName(owner); h.setEmail(email); h.setLocation(location); h.setNumberOfMembers(members); return h; }
    private Challenge challenge(String name, String description, double target, LocalDate start, LocalDate end) { Challenge c = new Challenge(); c.setChallengeName(name); c.setDescription(description); c.setTargetReductionPercentage(target); c.setStartDate(start); c.setEndDate(end); return c; }
    private void log(ActivityLogRepository logs, Household household, ActivityType type, double quantity, String unit, int monthsAgo) { ActivityLog a = new ActivityLog(); a.setHousehold(household); a.setActivityType(type); a.setQuantity(quantity); a.setUnit(unit); a.setActivityDate(LocalDate.now().minusMonths(monthsAgo)); a.setEmissionValues(type == ActivityType.ELECTRICITY ? .82 : .21); logs.save(a); }
    private void join(ParticipationRepository repository, Household household, Challenge challenge) { Participation p = new Participation(); p.setHousehold(household); p.setChallenge(challenge); p.setBaselineFootprint(200); p.setCurrentFootprint(160); p.setReductionPercentage(20); p.setProgressStatus(ProgressStatus.TARGET_ACHIEVED); repository.save(p); }
}

package com.example.carbontrack.service;

import com.example.carbontrack.entity.ActivityType;
import com.example.carbontrack.repository.ActivityLogRepository;
import org.springframework.stereotype.Service;
import java.time.YearMonth;
import java.util.Map;

@Service
public class CarbonFootprintService {
    private static final Map<ActivityType, Double> FACTORS = Map.of(
        ActivityType.CAR_TRAVEL, 0.21, ActivityType.BUS_TRAVEL, 0.10, ActivityType.BIKE_TRAVEL, 0.0,
        ActivityType.ELECTRICITY, 0.82, ActivityType.LPG, 2.98, ActivityType.WASTE, 0.45, ActivityType.FLIGHT, 0.25);
    private final ActivityLogRepository activityLogRepository;
    public CarbonFootprintService(ActivityLogRepository repository) { this.activityLogRepository = repository; }
    public double factorFor(ActivityType type) { return FACTORS.getOrDefault(type, 0.0); }
    public double calculateMonthlyFootprint(Long householdId, int year, int month) {
        YearMonth period = YearMonth.of(year, month);
        return activityLogRepository.sumEmissions(householdId, period.atDay(1), period.atEndOfMonth());
    }
    public double calculateReduction(double previous, double current) { return previous <= 0 ? 0 : ((previous - current) / previous) * 100; }
}

package com.example.carbontrack.service;

import com.example.carbontrack.entity.*;
import com.example.carbontrack.exception.*;
import com.example.carbontrack.repository.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Service
public class ActivityLogService {
    private final ActivityLogRepository repository;
    private final HouseholdRepository householdRepository;
    private final CarbonFootprintService footprintService;
    public ActivityLogService(ActivityLogRepository repository, HouseholdRepository householdRepository, CarbonFootprintService footprintService) { this.repository = repository; this.householdRepository = householdRepository; this.footprintService = footprintService; }
    public List<ActivityLog> findAll(Long householdId, ActivityType type, Integer year, Integer month) { YearMonth p = YearMonth.of(year == null ? LocalDate.now().getYear() : year, month == null ? LocalDate.now().getMonthValue() : month); return repository.filter(householdId, type, month == null && year == null ? LocalDate.of(2000, 1, 1) : p.atDay(1), month == null && year == null ? LocalDate.of(2100, 12, 31) : p.atEndOfMonth()); }
    public ActivityLog findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Activity log not found")); }
    public ActivityLog save(ActivityLog log, Long householdId) {
        if (log.getQuantity() <= 0 || log.getActivityType() == null) {
            throw new InvalidActivityException("Activity type and quantity greater than zero are required");
        }
        log.setHousehold(householdRepository.findById(householdId).orElseThrow(() -> new ResourceNotFoundException("Household not found")));
        log.setEmissionValues(footprintService.factorFor(log.getActivityType()));
        return repository.save(log);
    }
    public void delete(Long id) { repository.delete(findById(id)); }
    public long count() { return repository.count(); }
    public List<ActivityLog> recent() { return repository.findTop10ByOrderByActivityDateDesc(); }
    public double currentMonthTotal() { YearMonth p = YearMonth.now(); return repository.sumAllEmissions(p.atDay(1), p.atEndOfMonth()); }
}

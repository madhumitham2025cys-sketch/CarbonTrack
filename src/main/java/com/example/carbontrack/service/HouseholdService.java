package com.example.carbontrack.service;

import com.example.carbontrack.entity.Household;
import com.example.carbontrack.exception.ResourceNotFoundException;
import com.example.carbontrack.repository.HouseholdRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HouseholdService {
    private final HouseholdRepository repository;
    public HouseholdService(HouseholdRepository repository) { this.repository = repository; }
    public List<Household> findAll(String search) { return search == null || search.isBlank() ? repository.findAll() : repository.findByHouseholdNameContainingIgnoreCaseOrOwnerNameContainingIgnoreCase(search, search); }
    public Household findById(Long id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Household not found")); }
    public Household save(Household household) { return repository.save(household); }
    public void delete(Long id) { repository.delete(findById(id)); }
    public long count() { return repository.count(); }
}

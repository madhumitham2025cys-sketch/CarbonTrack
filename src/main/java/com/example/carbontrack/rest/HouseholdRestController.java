package com.example.carbontrack.rest;

import com.example.carbontrack.entity.Household;
import com.example.carbontrack.service.HouseholdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/households")
@Tag(name = "Households", description = "CRUD operations for CarbonTrack households")
public class HouseholdRestController {
    private final HouseholdService service;
    public HouseholdRestController(HouseholdService service) { this.service = service; }

    @GetMapping
    @Operation(summary = "Get all households")
    public List<ApiModels.HouseholdResponse> findAll(@RequestParam(required = false) String search) {
        return service.findAll(search).stream().map(ApiModels.HouseholdResponse::from).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a household by ID")
    @ApiResponse(responseCode = "404", description = "Household not found")
    public ApiModels.HouseholdResponse findById(@PathVariable Long id) { return ApiModels.HouseholdResponse.from(service.findById(id)); }

    @PostMapping
    @Operation(summary = "Create a household")
    @ApiResponse(responseCode = "201", description = "Household created")
    public ResponseEntity<ApiModels.HouseholdResponse> create(@Valid @RequestBody Household household) {
        return RestResponse.created(ApiModels.HouseholdResponse.from(service.save(household)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a household")
    public ApiModels.HouseholdResponse update(@PathVariable Long id, @Valid @RequestBody Household household) {
        service.findById(id);
        household.setId(id);
        return ApiModels.HouseholdResponse.from(service.save(household));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a household")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

package com.example.carbontrack.rest;

import com.example.carbontrack.entity.*;
import com.example.carbontrack.service.ActivityLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/activity-logs")
@Tag(name = "Activity Logs", description = "Household activity and emissions")
public class ActivityLogRestController {
    private final ActivityLogService service;
    public ActivityLogRestController(ActivityLogService service) { this.service = service; }

    @GetMapping
    @Operation(summary = "Get activity logs", description = "Optional filters: household, activity type, year, and month")
    public List<ApiModels.ActivityLogResponse> findAll(@RequestParam(required = false) Long householdId, @RequestParam(required = false) ActivityType type, @RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month) {
        return service.findAll(householdId, type, year, month).stream().map(ApiModels.ActivityLogResponse::from).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an activity log by ID")
    public ApiModels.ActivityLogResponse findById(@PathVariable Long id) { return ApiModels.ActivityLogResponse.from(service.findById(id)); }

    @PostMapping
    @Operation(summary = "Create an activity log", description = "Emission factor and calculated emission are assigned by the service")
    @ApiResponse(responseCode = "201", description = "Activity log created")
    public ResponseEntity<ApiModels.ActivityLogResponse> create(@RequestParam Long householdId, @Valid @RequestBody ActivityLog log) {
        return RestResponse.created(ApiModels.ActivityLogResponse.from(service.save(log, householdId)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an activity log")
    public ApiModels.ActivityLogResponse update(@PathVariable Long id, @RequestParam Long householdId, @Valid @RequestBody ActivityLog log) {
        service.findById(id);
        log.setId(id);
        return ApiModels.ActivityLogResponse.from(service.save(log, householdId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an activity log")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

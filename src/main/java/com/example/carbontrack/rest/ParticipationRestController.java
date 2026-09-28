package com.example.carbontrack.rest;

import com.example.carbontrack.entity.Participation;
import com.example.carbontrack.service.ParticipationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/participations")
@Tag(name = "Participation", description = "Household challenge participation")
public class ParticipationRestController {
    private final ParticipationService service;
    public ParticipationRestController(ParticipationService service) { this.service = service; }

    @GetMapping
    @Operation(summary = "Get all participations")
    public List<ApiModels.ParticipationResponse> findAll() { return service.findAll().stream().map(ApiModels.ParticipationResponse::from).toList(); }

    @GetMapping("/{id}")
    @Operation(summary = "Get a participation by ID")
    public ApiModels.ParticipationResponse findById(@PathVariable Long id) { return ApiModels.ParticipationResponse.from(service.findById(id)); }

    @PostMapping
    @Operation(summary = "Join a challenge", description = "Progress and footprint values are calculated by the service")
    @ApiResponse(responseCode = "201", description = "Participation created")
    public ResponseEntity<ApiModels.ParticipationResponse> create(@RequestParam Long householdId, @RequestParam Long challengeId, @Valid @RequestBody Participation participation) {
        return RestResponse.created(ApiModels.ParticipationResponse.from(service.save(participation, householdId, challengeId)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update participation")
    public ApiModels.ParticipationResponse update(@PathVariable Long id, @RequestParam Long householdId, @RequestParam Long challengeId, @Valid @RequestBody Participation participation) {
        service.findById(id);
        participation.setId(id);
        return ApiModels.ParticipationResponse.from(service.save(participation, householdId, challengeId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete participation")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

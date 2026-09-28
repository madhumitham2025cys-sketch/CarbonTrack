package com.example.carbontrack.rest;

import com.example.carbontrack.entity.*;
import com.example.carbontrack.service.ChallengeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/challenges")
@Tag(name = "Challenges", description = "Carbon reduction challenges")
public class ChallengeRestController {
    private final ChallengeService service;
    public ChallengeRestController(ChallengeService service) { this.service = service; }

    @GetMapping
    @Operation(summary = "Get all challenges")
    public List<ApiModels.ChallengeResponse> findAll(@RequestParam(required = false) ChallengeStatus status) { return service.findAll(status).stream().map(ApiModels.ChallengeResponse::from).toList(); }

    @GetMapping("/{id}")
    @Operation(summary = "Get a challenge by ID")
    public ApiModels.ChallengeResponse findById(@PathVariable Long id) { return ApiModels.ChallengeResponse.from(service.findById(id)); }

    @PostMapping
    @Operation(summary = "Create a challenge")
    @ApiResponse(responseCode = "201", description = "Challenge created")
    public ResponseEntity<ApiModels.ChallengeResponse> create(@Valid @RequestBody Challenge challenge) { return RestResponse.created(ApiModels.ChallengeResponse.from(service.save(challenge))); }

    @PutMapping("/{id}")
    @Operation(summary = "Update a challenge")
    public ApiModels.ChallengeResponse update(@PathVariable Long id, @Valid @RequestBody Challenge challenge) { service.findById(id); challenge.setId(id); return ApiModels.ChallengeResponse.from(service.save(challenge)); }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a challenge")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

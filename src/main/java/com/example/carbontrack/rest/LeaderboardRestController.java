package com.example.carbontrack.rest;

import com.example.carbontrack.service.LeaderboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/leaderboard")
@Tag(name = "Leaderboard", description = "Monthly carbon reduction rankings")
public class LeaderboardRestController {
    private final LeaderboardService service;
    public LeaderboardRestController(LeaderboardService service) { this.service = service; }

    @GetMapping
    @Operation(summary = "Get the top monthly reductions")
    public List<ApiModels.LeaderboardResponse> leaderboard() {
        AtomicInteger rank = new AtomicInteger(1);
        return service.monthlyLeaderboard().stream().map(p -> new ApiModels.LeaderboardResponse(rank.getAndIncrement(), p.getHousehold().getHouseholdName(), p.getBaselineFootprint(), p.getCurrentFootprint(), p.getReductionPercentage())).toList();
    }
}

package com.example.carbontrack.controller;

import com.example.carbontrack.service.LeaderboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LeaderboardController {
    private final LeaderboardService service;
    public LeaderboardController(LeaderboardService service) { this.service = service; }
    @GetMapping("/leaderboard") public String leaderboard(Model model) { model.addAttribute("leaderboard", service.monthlyLeaderboard()); return "leaderboard"; }
}

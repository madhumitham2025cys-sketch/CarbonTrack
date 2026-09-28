package com.example.carbontrack.service;

import com.example.carbontrack.entity.Participation;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LeaderboardService {
    private final ParticipationService participationService;
    public LeaderboardService(ParticipationService participationService) { this.participationService = participationService; }
    public List<Participation> monthlyLeaderboard() { return participationService.topFive(); }
}

package com.example.carbontrack.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class DashboardService {
    private final HouseholdService households; private final ActivityLogService activities; private final ChallengeService challenges; private final ParticipationService participations; private final LeaderboardService leaderboard;
    public DashboardService(HouseholdService h, ActivityLogService a, ChallengeService c, ParticipationService p, LeaderboardService l) { households = h; activities = a; challenges = c; participations = p; leaderboard = l; }
    public Map<String, Object> summary() { double best = leaderboard.monthlyLeaderboard().stream().mapToDouble(p -> p.getReductionPercentage()).max().orElse(0); return Map.of("households", households.count(), "activities", activities.count(), "activeChallenges", challenges.countActive(), "participants", participations.count(), "thisMonth", activities.currentMonthTotal(), "bestReduction", best, "recent", activities.recent(), "challenges", challenges.active(), "leaderboard", leaderboard.monthlyLeaderboard()); }
}

package com.example.carbontrack.controller;

import com.example.carbontrack.entity.Participation;
import com.example.carbontrack.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/participations")
public class ParticipationController {
    private final ParticipationService service; private final HouseholdService households; private final ChallengeService challenges;
    public ParticipationController(ParticipationService service, HouseholdService households, ChallengeService challenges) { this.service = service; this.households = households; this.challenges = challenges; }
    @GetMapping public String list(Model model) { model.addAttribute("participations", service.findAll()); return "participations"; }
    @GetMapping("/new") public String form(Model model) { model.addAttribute("participation", new Participation()); model.addAttribute("households", households.findAll(null)); model.addAttribute("challenges", challenges.findAll(null)); return "participation-form"; }
    @GetMapping("/{id}") public String view(@PathVariable Long id, Model model) { model.addAttribute("participation", service.findById(id)); return "participation-view"; }
    @PostMapping("/save") public String save(Participation participation, @RequestParam Long householdId, @RequestParam Long challengeId) { service.save(participation, householdId, challengeId); return "redirect:/participations"; }
    @PostMapping("/{id}/delete") public String delete(@PathVariable Long id) { service.delete(id); return "redirect:/participations"; }
}

package com.example.carbontrack.controller;

import com.example.carbontrack.entity.*;
import com.example.carbontrack.service.ChallengeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/challenges")
public class ChallengeController {
    private final ChallengeService service;
    public ChallengeController(ChallengeService service) { this.service = service; }
    @GetMapping public String list(@RequestParam(required = false) ChallengeStatus status, Model model) { model.addAttribute("challenges", service.findAll(status)); model.addAttribute("statuses", ChallengeStatus.values()); return "challenges"; }
    @GetMapping("/new") public String form(Model model) { model.addAttribute("challenge", new Challenge()); return "challenge-form"; }
    @GetMapping("/{id}") public String view(@PathVariable Long id, Model model) { model.addAttribute("challenge", service.findById(id)); return "challenge-view"; }
    @GetMapping("/{id}/edit") public String edit(@PathVariable Long id, Model model) { model.addAttribute("challenge", service.findById(id)); return "challenge-form"; }
    @PostMapping("/save") public String save(@ModelAttribute Challenge challenge) { service.save(challenge); return "redirect:/challenges"; }
    @PostMapping("/{id}/delete") public String delete(@PathVariable Long id) { service.delete(id); return "redirect:/challenges"; }
}

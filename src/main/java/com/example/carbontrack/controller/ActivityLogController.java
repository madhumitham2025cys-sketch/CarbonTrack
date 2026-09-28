package com.example.carbontrack.controller;

import com.example.carbontrack.entity.*;
import com.example.carbontrack.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/activity-logs")
public class ActivityLogController {
    private final ActivityLogService service; private final HouseholdService households;
    public ActivityLogController(ActivityLogService service, HouseholdService households) { this.service = service; this.households = households; }
    @GetMapping public String list(@RequestParam(required = false) Long householdId, @RequestParam(required = false) ActivityType type, @RequestParam(required = false) Integer year, @RequestParam(required = false) Integer month, Model model) { model.addAttribute("logs", service.findAll(householdId, type, year, month)); model.addAttribute("households", households.findAll(null)); model.addAttribute("types", ActivityType.values()); return "activitylogs"; }
    @GetMapping("/new") public String form(Model model) { model.addAttribute("log", new ActivityLog()); model.addAttribute("households", households.findAll(null)); model.addAttribute("types", ActivityType.values()); return "activitylog-form"; }
    @GetMapping("/{id}") public String view(@PathVariable Long id, Model model) { model.addAttribute("log", service.findById(id)); return "activitylog-view"; }
    @GetMapping("/{id}/edit") public String edit(@PathVariable Long id, Model model) { model.addAttribute("log", service.findById(id)); model.addAttribute("households", households.findAll(null)); model.addAttribute("types", ActivityType.values()); return "activitylog-form"; }
    @PostMapping("/save") public String save(@ModelAttribute ActivityLog log, @RequestParam Long householdId) { service.save(log, householdId); return "redirect:/activity-logs"; }
    @PostMapping("/{id}/delete") public String delete(@PathVariable Long id) { service.delete(id); return "redirect:/activity-logs"; }
}

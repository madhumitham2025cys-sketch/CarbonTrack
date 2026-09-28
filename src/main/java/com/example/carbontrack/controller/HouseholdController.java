package com.example.carbontrack.controller;

import com.example.carbontrack.entity.Household;
import com.example.carbontrack.service.HouseholdService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/households")
public class HouseholdController {
    private final HouseholdService service;
    public HouseholdController(HouseholdService service) { this.service = service; }
    @GetMapping public String list(@RequestParam(required = false) String search, Model model) { model.addAttribute("households", service.findAll(search)); model.addAttribute("search", search); return "households"; }
    @GetMapping("/new") public String form(Model model) { model.addAttribute("household", new Household()); return "household-form"; }
    @GetMapping("/{id}") public String view(@PathVariable Long id, Model model) { model.addAttribute("household", service.findById(id)); return "household-view"; }
    @PostMapping("/save") public String save(@Valid @ModelAttribute Household household, BindingResult result) { if (result.hasErrors()) return "household-form"; service.save(household); return "redirect:/households"; }
    @GetMapping("/{id}/edit") public String edit(@PathVariable Long id, Model model) { model.addAttribute("household", service.findById(id)); return "household-form"; }
    @PostMapping("/{id}/delete") public String delete(@PathVariable Long id) { service.delete(id); return "redirect:/households"; }
}

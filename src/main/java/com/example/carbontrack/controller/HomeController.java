package com.example.carbontrack.controller;

import com.example.carbontrack.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final DashboardService dashboard;
    public HomeController(DashboardService dashboard) { this.dashboard = dashboard; }
    @GetMapping({"/", "/dashboard"}) public String home(Model model) { model.addAllAttributes(dashboard.summary()); return "index"; }
}

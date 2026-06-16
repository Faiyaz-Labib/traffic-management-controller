package com.dhakatraffic.tms.controller;

import com.dhakatraffic.tms.repository.RoadSegmentRepository;
import com.dhakatraffic.tms.repository.TrafficDataRepository;
import com.dhakatraffic.tms.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final UserRepository userRepository;
    private final RoadSegmentRepository segmentRepository;
    private final TrafficDataRepository trafficDataRepository;

    public ViewController(UserRepository userRepository, 
                          RoadSegmentRepository segmentRepository, 
                          TrafficDataRepository trafficDataRepository) {
        this.userRepository = userRepository;
        this.segmentRepository = segmentRepository;
        this.trafficDataRepository = trafficDataRepository;
    }

    // 1. Citizen Map View Dashboard Route
    @GetMapping("/dashboard")
    public String getDashboardView(Model model) {
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("totalSegments", segmentRepository.count());
        model.addAttribute("trafficStream", trafficDataRepository.findAll());
        return "dashboard";
    }

    // 2. Central Unified Sign-In Page Route
    @GetMapping("/login")
    public String getLoginView() {
        return "login";
    }

    // 3. Citizen Registration / Sign-Up Page Route
    @GetMapping("/register")
    public String getRegisterView() {
        return "register";
    }

    // 4. Admin Incident Verification Queue Route
    @GetMapping("/admin/verify")
    public String getAdminVerifyView() {
        return "admin";
    }
}
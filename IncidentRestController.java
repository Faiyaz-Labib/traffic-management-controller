package com.dhakatraffic.tms.controller;

import com.dhakatraffic.tms.model.UserTrafficReport;
import com.dhakatraffic.tms.model.ReportStatus;
import com.dhakatraffic.tms.repository.TrafficReportRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentRestController {

    private final TrafficReportRepository reportRepository;

    public IncidentRestController(TrafficReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // 1. Endpoint to get all dynamic incident points to show on the Map
    @GetMapping
    public List<UserTrafficReport> getAllIncidents() {
        return reportRepository.findAll();
    }

    // 2. Endpoint to filter active or pending reports for Traffic Managers
    @GetMapping("/pending")
    public List<UserTrafficReport> getPendingIncidents() {
        return reportRepository.findByStatus(ReportStatus.pending);
    }

    // 3. Endpoint for Public Users to submit a live traffic incident report
    @PostMapping("/report")
    public UserTrafficReport createReport(@RequestBody UserTrafficReport newReport) {
        return reportRepository.save(newReport);
    }
}
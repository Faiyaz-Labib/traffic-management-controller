package com.dhakatraffic.tms.controller;

import com.dhakatraffic.tms.model.*;
import com.dhakatraffic.tms.repository.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TrafficManagementController {

    private final RoadSegmentRepository segmentRepository;
    private final TrafficDataRepository trafficDataRepository;
    private final PenaltyRepository penaltyRepository;
    private final UserRepository userRepository;

    public TrafficManagementController(RoadSegmentRepository segmentRepository,
                                       TrafficDataRepository trafficDataRepository,
                                       PenaltyRepository penaltyRepository,
                                       UserRepository userRepository) {
        this.segmentRepository = segmentRepository;
        this.trafficDataRepository = trafficDataRepository;
        this.penaltyRepository = penaltyRepository;
        this.userRepository = userRepository;
    }

    // 1. Dashboard Metrics Endpoint (For Admin View)
    @GetMapping("/admin/dashboard-stats")
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        stats.put("totalMonitoredSegments", segmentRepository.count());
        stats.put("systemStatus", "🟢 Operational - Active");
        return stats;
    }

    // 2. Map Stream Data Endpoint (Fetches real-time speeds across network)
    @GetMapping("/maps/traffic-stream")
    public List<TrafficData> getLiveTrafficStream() {
        return trafficDataRepository.findAll();
    }

    // 3. User Penalty Dashboard Lookup
    @GetMapping("/public/user/{userId}/penalties")
    public List<TrafficPenalty> getUserPenalties(@PathVariable Integer userId) {
        return penaltyRepository.findByUserId(userId);
    }

    // 4. Manual Node Speed Update Override (For Traffic Managers)
    @PostMapping("/manager/update-speed")
    public TrafficData updateSegmentSpeed(@RequestBody TrafficData customData) {
        return trafficDataRepository.save(customData);
    }
}
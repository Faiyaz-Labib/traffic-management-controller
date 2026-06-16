package com.dhakatraffic.tms.controller;

import com.dhakatraffic.tms.model.PublicTransport;
import com.dhakatraffic.tms.model.EmergencyVehicle;
import com.dhakatraffic.tms.repository.PublicTransportRepository;
import com.dhakatraffic.tms.repository.EmergencyVehicleRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transit")
public class TransitTrackingController {

    private final PublicTransportRepository transportRepository;
    private final EmergencyVehicleRepository emergencyRepository;

    public TransitTrackingController(PublicTransportRepository transportRepository,
                                     EmergencyVehicleRepository emergencyRepository) {
        this.transportRepository = transportRepository;
        this.emergencyRepository = emergencyRepository;
    }

    // 1. Get Live Locations of All Public Buses and Metro Trains
    @GetMapping("/public-transport")
    public List<PublicTransport> getAllTransit() {
        return transportRepository.findAll();
    }

    // 2. Filter for Emergency Vehicles currently responding to a crisis (Priority Routing)
    @GetMapping("/emergency/active")
    public List<EmergencyVehicle> getActiveEmergencies() {
        return emergencyRepository.findByIsResponding(true);
    }
}
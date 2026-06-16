package com.dhakatraffic.tms.controller;

import com.dhakatraffic.tms.model.*;
import com.dhakatraffic.tms.repository.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class CityOperationsController {

    private final JunctionRepository junctionRepository;
    private final TrafficLightRepository trafficLightRepository;
    private final EventRepository eventRepository;
    private final RoutePlanningRepository routePlanningRepository;

    public CityOperationsController(JunctionRepository junctionRepository,
                                    TrafficLightRepository trafficLightRepository,
                                    EventRepository eventRepository,
                                    RoutePlanningRepository routePlanningRepository) {
        this.junctionRepository = junctionRepository;
        this.trafficLightRepository = trafficLightRepository;
        this.eventRepository = eventRepository;
        this.routePlanningRepository = routePlanningRepository;
    }

    @GetMapping("/junctions")
    public List<Junction> getJunctions() { return junctionRepository.findAll(); }

    @GetMapping("/lights")
    public List<TrafficLight> getLights() { return trafficLightRepository.findAll(); }

    @GetMapping("/events/high-impact")
    public List<Event> getHighImpactEvents() { return eventRepository.findAll(); }

    @PostMapping("/route/plan")
    public RoutePlanning saveJourneyLog(@RequestBody RoutePlanning journey) {
        return routePlanningRepository.save(journey);
    }
}
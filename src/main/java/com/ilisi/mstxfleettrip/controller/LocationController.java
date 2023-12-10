package com.ilisi.mstxfleettrip.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/location.getPassengersLocation")
    public void getPassengersLocation(SimpMessageHeaderAccessor headerAccessor) {
        String driverId = headerAccessor.getSessionAttributes().get("userId").toString();
        List<String> passengers = (List<String>) headerAccessor.getSessionAttributes().get("passengers");

        // TODO: get passengers location from Redis using passengers list

        log.info("Driver {} requested passengers location", driverId);
        log.info("Passengers: {}", passengers.toString());

    }

    @MessageMapping("/location.getDriverLocation")
    public void getDriverLocation(SimpMessageHeaderAccessor headerAccessor) {
        String passengerId = headerAccessor.getSessionAttributes().get("userId").toString();

        // TODO: get driver location from Redis using passengerId

        log.info("Passenger {} requested driver location", passengerId);
    }
}

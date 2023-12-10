package com.ilisi.mstxfleettrip.controller;

import com.ilisi.mstxfleettrip.dto.UserLocationDto;
import com.ilisi.mstxfleettrip.service.RedisClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RedisClientService redisClientService;

    @MessageMapping("/location.getPassengersLocation")
    public void getPassengersLocation(SimpMessageHeaderAccessor headerAccessor) {
        String driverId = Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId").toString();
        List<String> passengers = (List<String>) headerAccessor.getSessionAttributes().get("passengers");

        List<UserLocationDto> passengerLocations = redisClientService.getPassengerLocations(passengers);
        log.info("Driver {} requested passengers location", driverId);
        log.info("Passengers: {}", passengers);

        simpMessagingTemplate
                .convertAndSend(String.format("/topic/location.getPassengersLocation/%s", driverId),
                        Map.of("passengerLocations", passengerLocations));
    }

    @MessageMapping("/location.getDriverLocation")
    public void getDriverLocation(SimpMessageHeaderAccessor headerAccessor) {
        String passengerId = Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("userId").toString();

        UserLocationDto driverLocation = redisClientService.findDriverInSameTrip(passengerId);

        log.info("Passenger {} requested driver location {}", passengerId, driverLocation);

        simpMessagingTemplate
                .convertAndSend(String.format("/topic/location.getDriverLocation/%s", passengerId),
                        Map.of("driverLocation", driverLocation));
    }
}

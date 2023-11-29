package com.ilisi.mstxfleettrip.controller;


import com.ilisi.mstxfleettrip.dto.PathDto;
import com.ilisi.mstxfleettrip.service.PostgisClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;


@Controller
@RequiredArgsConstructor
@Slf4j
public class TripController {

    private final PostgisClientService postgisClientService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final KafkaTemplate<String, Map<String,Object>> kafkaTemplate;
    private static final double RADIUS_IN_METERS = 1000;
    @MessageMapping("/trip.initialize")
    public void route(@Payload PathDto pathDto, SimpMessageHeaderAccessor headerAccessor) {
        String passengerId = headerAccessor.getSessionAttributes().get("userId").toString();
        log.info("Received message: " + pathDto.toString());

        kafkaTemplate.send("trip",
                Map.of("passengerId", passengerId,
                        "endLatitude", pathDto.getEndLatitude(),
                        "endLongitude", pathDto.getEndLongitude())
        );

        String path = postgisClientService.findPath(
                pathDto.getStartLatitude(),
                pathDto.getStartLongitude(),
                pathDto.getEndLatitude(),
                pathDto.getEndLongitude()
        );
        path = path.substring(1, path.length() - 1);
        log.info("Path: " + path);
        simpMessagingTemplate.convertAndSend("/topic/trip.path/"+passengerId, Map.of("path", path));
    }

    @MessageMapping("/trip.accept")
    public void acceptTrip(@Payload String passengerId, SimpMessageHeaderAccessor headerAccessor) {
        String driverId = headerAccessor.getSessionAttributes().get("userId").toString();
        log.info("passenger message: " + passengerId);
        log.info("driver message: " + driverId);
        kafkaTemplate.send("trip",
                Map.of("passengerId", passengerId,
                        "driverId", driverId)
        );
        // TODO : get the path between the driver and the passenger
        simpMessagingTemplate.convertAndSend("/topic/trip.path/"+driverId, Map.of("status", true));
        simpMessagingTemplate.convertAndSend("/topic/trip.path/"+passengerId, Map.of("status", true));
    }

    @MessageMapping("/nearbyUsers")
    @SendTo("/topic/nearbyUsers")
    public Map<String,String> nearbyUsers(@Payload String userId) {
        log.info("Received message: " + userId);

        String nearbyUsers= postgisClientService.findNearbyOnlineUsersByUserId(userId, RADIUS_IN_METERS);
        log.info("Nearby users: " + nearbyUsers);
        return Map.of("nearbyUsers", nearbyUsers);
    }
}

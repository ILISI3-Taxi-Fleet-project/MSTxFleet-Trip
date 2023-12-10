package com.ilisi.mstxfleettrip.controller;


import com.ilisi.mstxfleettrip.dto.PathDto;
import com.ilisi.mstxfleettrip.dto.TripDto;
import com.ilisi.mstxfleettrip.service.PostgisClientService;
import com.ilisi.mstxfleettrip.service.RedisClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;


@Controller
@RequiredArgsConstructor
@Slf4j
public class TripController {

    private final PostgisClientService postgisClientService;
    private final RedisClientService redisClientService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final KafkaTemplate<String, Map<String,Object>> kafkaTemplate;
    private static final double RADIUS_IN_METERS = 1000;
    @MessageMapping("/trip.initialize")
    public void route(@Payload PathDto pathDto, SimpMessageHeaderAccessor headerAccessor) {
        String passengerId = headerAccessor.getSessionAttributes().get("userId").toString();
        log.info("Received message: " + pathDto.toString());
        kafkaTemplate.send("trip",
                Map.of(
                        "tripId", UUID.randomUUID(),
                        "passengerId", passengerId,
                        "startLatitude", pathDto.getStartLatitude(),
                        "startLongitude", pathDto.getStartLongitude(),
                        "endLatitude", pathDto.getEndLatitude(),
                        "endLongitude", pathDto.getEndLongitude(),
                        "status", "pending",
                        "created_at", Instant.now().toString()
                )
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
    public void acceptTrip(@Payload Map<String,Object> passenger, SimpMessageHeaderAccessor headerAccessor) {
        String driverId = headerAccessor.getSessionAttributes().get("userId").toString();
        String passengerId = passenger.get("passengerId").toString();
        log.info("passenger message: " + passengerId);
        log.info("driver message: " + driverId);
        TripDto tripDto = redisClientService.getTrip(passengerId);
        kafkaTemplate.send("trip",
                Map.of("passengerId", tripDto.getPassengerId(),
                        "tripId", tripDto.getTripId(),
                        "driverId", driverId,
                        "status", "accepted",
                        "updated_at", Instant.now().toString()
                )
        );
        String path = postgisClientService.findPath(passengerId, driverId);
        path = path.substring(1, path.length() - 1);
        log.info("Path: " + path);
        simpMessagingTemplate.convertAndSend("/topic/trip.path/"+driverId, Map.of("path", path));
        simpMessagingTemplate.convertAndSend("/topic/trip.path/"+passengerId, Map.of("path", path));
    }

    @MessageMapping("/trip.nearbyUsers")
    public void nearbyUsers(SimpMessageHeaderAccessor headerAccessor) {
        String userId = headerAccessor.getSessionAttributes().get("userId").toString();
        log.info("Received message: " + userId);

        String nearbyUsers= postgisClientService.findNearbyOnlineUsersByUserId(userId, RADIUS_IN_METERS);
        log.info("Nearby users: " + nearbyUsers);
        simpMessagingTemplate.convertAndSend("/topic/trip.nearbyUsers/"+userId, Map.of("nearbyUsers", nearbyUsers));
    }
}

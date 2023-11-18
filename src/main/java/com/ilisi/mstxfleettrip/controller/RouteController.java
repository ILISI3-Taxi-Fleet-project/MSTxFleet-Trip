package com.ilisi.mstxfleettrip.controller;


import com.ilisi.mstxfleettrip.dto.TripDto;
import com.ilisi.mstxfleettrip.service.PostgisClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
@Slf4j
public class RouteController {
    private final PostgisClientService postgisClientService;

    @MessageMapping("/route")
    @SendTo("/topic/route")
    public String route(@Payload TripDto tripDto) {
        log.info("Received message: " + tripDto.toString());
        String path = postgisClientService.findPath(
                tripDto.getStartLatitude(),
                tripDto.getStartLongitude(),
                tripDto.getEndLatitude(),
                tripDto.getEndLongitude()
        );
        log.info("Path: " + path);
        return path;
    }
}

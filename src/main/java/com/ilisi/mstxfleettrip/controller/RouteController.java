package com.ilisi.mstxfleettrip.controller;


import com.ilisi.mstxfleettrip.dto.TripDto;
import com.ilisi.mstxfleettrip.service.PostgisClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;


@Controller
@RequiredArgsConstructor
@Slf4j
public class RouteController {
    private final PostgisClientService postgisClientService;

    @MessageMapping("/route")
    @SendTo("/topic/route")
    public Map<String,String> route(@Payload TripDto tripDto) {
        log.info("Received message: " + tripDto.toString());
        String path = postgisClientService.findPath(
                tripDto.getStartLatitude(),
                tripDto.getStartLongitude(),
                tripDto.getEndLatitude(),
                tripDto.getEndLongitude()
        );
        path = path.substring(1, path.length() - 1);
        log.info("Path: " + path);
        return Map.of("path", path);
    }
}

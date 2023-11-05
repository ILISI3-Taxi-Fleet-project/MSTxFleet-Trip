package com.ilisi.mstxfleettrip.controller;


import com.ilisi.mstxfleettrip.entity.Coordinate;
import com.ilisi.mstxfleettrip.entity.Route;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
@Slf4j
public class RouteController {

    @MessageMapping("/route")
    @SendTo("/topic/route")
    public Route route(@Payload Coordinate cordinate) {
        log.info("Message received: {"+
                "latitude: "+cordinate.getLatitude()+
                ", longitude: "+cordinate.getLongitude()+
                "}");
        Route route = new Route(new ArrayList<Coordinate>());
        route.addCoordinate(new Coordinate(33.70639, -7.3533433));
        route.addCoordinate(new Coordinate(33.707124, -7.353999));
        route.addCoordinate(new Coordinate(33.705118, -7.357584));
        route.addCoordinate(new Coordinate(33.705664, -7.360265));
        route.addCoordinate(new Coordinate(33.707173, -7.362968));
        route.addCoordinate(new Coordinate(33.701802, -7.376807));
        route.addCoordinate(new Coordinate(33.701523, -7.378711));

        return route;
    }
}

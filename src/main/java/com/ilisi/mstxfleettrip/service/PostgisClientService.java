package com.ilisi.mstxfleettrip.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name ="${service.postgis.name}", url = "${service.postgis.url}")
public interface PostgisClientService {
    @GetMapping("/userLocations/search/findPath")
    String findPath(@RequestParam("startLatitude") double startLatitude,
                    @RequestParam("startLongitude") double startLongitude,
                    @RequestParam("endLatitude") double endLatitude,
                    @RequestParam("endLongitude") double endLongitude);

    @GetMapping("/userLocations/search/findNearbyOnlineUsersByUserId")
    String findNearbyOnlineUsersByUserId(@RequestParam("userId") String userId,
                                         @RequestParam("radiusInMeters") double radiusInMeters);
}

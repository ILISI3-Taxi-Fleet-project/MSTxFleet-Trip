package com.ilisi.mstxfleettrip.service;

import com.ilisi.mstxfleettrip.dto.TripDto;
import com.ilisi.mstxfleettrip.dto.UserLocationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@FeignClient(name ="${service.redis.name}", url = "${service.redis.url}")
public interface RedisClientService {

    @GetMapping("/trip/{passengerId}")
    TripDto getTrip(@PathVariable("passengerId") String passengerId);

    @GetMapping("/trip/findDriverInSameTrip")
    UserLocationDto findDriverInSameTrip(@RequestParam("passengerId") String passengerId);

    @GetMapping("/userLocations/getMultiLocations")
    List<UserLocationDto> getPassengerLocations(@RequestParam("passengerIds") Collection<String> passengerIds);

}

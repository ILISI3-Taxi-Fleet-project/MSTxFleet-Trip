package com.ilisi.mstxfleettrip.service;

import com.ilisi.mstxfleettrip.dto.TripDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="${service.redis.name}", url = "${service.redis.url}")
public interface RedisClientService {

    @GetMapping("/trip/{passengerId}")
    TripDto getTrip(@PathVariable("passengerId") String passengerId);
}

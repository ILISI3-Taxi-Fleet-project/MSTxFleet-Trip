package com.ilisi.mstxfleettrip.dto;


import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
@ToString
public class TripDto {
    private String tripId;

    private String status;

    private Location destination;

    private Location startLocation;

    private Instant createdAt;

    private Instant updatedAt;

    private String passengerId;

    private String driverId;
}

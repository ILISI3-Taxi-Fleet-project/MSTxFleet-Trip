package com.ilisi.mstxfleettrip.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TripDto {
    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;
}

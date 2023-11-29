package com.ilisi.mstxfleettrip.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PathDto {
    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;
}

package com.ilisi.mstxfleettrip.dto;


import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLocationDto {

    private String userId;
    private String location;

    private String userType;
    private boolean isOnline;

    private Instant createdAt;
    private Instant updatedAt;
}


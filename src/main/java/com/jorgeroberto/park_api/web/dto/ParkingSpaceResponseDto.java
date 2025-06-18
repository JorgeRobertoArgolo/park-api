package com.jorgeroberto.park_api.web.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ParkingSpaceResponseDto {
    private Long id;
    private String code;
    private String status;
}

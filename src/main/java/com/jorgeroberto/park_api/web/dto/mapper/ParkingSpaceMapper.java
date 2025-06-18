package com.jorgeroberto.park_api.web.dto.mapper;

import com.jorgeroberto.park_api.entities.ParkingSpace;
import com.jorgeroberto.park_api.web.dto.ParkingSpaceCreateDto;
import com.jorgeroberto.park_api.web.dto.ParkingSpaceResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingSpaceMapper {

    public static ParkingSpace toParkingSpace(ParkingSpaceCreateDto dto) {
        return new ModelMapper().map(dto, ParkingSpace.class);
    }

    public static ParkingSpaceResponseDto toDto(ParkingSpace parkingSpace) {
        return new ModelMapper().map(parkingSpace, ParkingSpaceResponseDto.class);
    }

}

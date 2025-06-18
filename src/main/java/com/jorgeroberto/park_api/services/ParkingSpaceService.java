package com.jorgeroberto.park_api.services;

import com.jorgeroberto.park_api.entities.ParkingSpace;
import com.jorgeroberto.park_api.exceptions.CodeUniqueViolationException;
import com.jorgeroberto.park_api.exceptions.EntityNotFoundException;
import com.jorgeroberto.park_api.repositories.ParkingSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ParkingSpaceService {
    private final ParkingSpaceRepository parkingSpaceRepository;

    @Transactional
    public ParkingSpace save(ParkingSpace parkingSpace) {
        try {
            return parkingSpaceRepository.save(parkingSpace);
        } catch (DataIntegrityViolationException e) {
            throw new CodeUniqueViolationException(
                    String.format("Parking Space with code : %s already exists", parkingSpace.getCode())
            );
        }
    }

    @Transactional(readOnly = true)
    public ParkingSpace findByCode(String code) {
        return parkingSpaceRepository.findByCode(code).orElseThrow(
                () -> new EntityNotFoundException(String.format("Parking Space with code : %s not found", code))
        );
    }
}

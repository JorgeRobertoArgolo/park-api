package com.jorgeroberto.park_api.repositories;

import com.jorgeroberto.park_api.entities.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {
    Optional<ParkingSpace> findByCode(String code);
}

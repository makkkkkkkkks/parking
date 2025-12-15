package com.parking.service.impl;

import com.parking.dto.ParkVehicleRequestDTO;
import com.parking.dto.ParkVehicleResponseDTO;
import com.parking.dto.ParkingStatusResponseDTO;
import com.parking.mapper.ParkingStatusMapper;
import com.parking.service.ParkVehicleService;
import com.parking.storage.ParkingStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkVehicleServiceImpl implements ParkVehicleService {

    private final ParkingStorage parkingStorage;
    private final ParkingStatusMapper parkingStatusMapper;

    @Override
    public ParkVehicleResponseDTO parkVehicle(ParkVehicleRequestDTO request) {
        return null;
    }

    @Override
    public ParkingStatusResponseDTO getParkingStatus() {
        return parkingStatusMapper.toParkingStatusResponseDTO(
                parkingStorage.getAvailableSpaces(),
                parkingStorage.getOccupiedSpaces()
        );
    }
}


package com.parking.service.impl;

import com.parking.dto.ParkVehicleRequestDTO;
import com.parking.dto.ParkVehicleResponseDTO;
import com.parking.dto.ParkingStatusResponseDTO;
import com.parking.service.ParkVehicleService;
import org.springframework.stereotype.Service;

@Service
public class ParkVehicleServiceImpl implements ParkVehicleService {
    @Override
    public ParkVehicleResponseDTO parkVehicle(ParkVehicleRequestDTO request) {
        return null;
    }

    @Override
    public ParkingStatusResponseDTO getParkingStatus() {
        return null;
    }
}


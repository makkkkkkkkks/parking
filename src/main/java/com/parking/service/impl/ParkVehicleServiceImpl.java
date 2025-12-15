package com.parking.service.impl;

import com.parking.dto.ParkVehicleRequestDTO;
import com.parking.exception.NoAvailableParkingSpacesException;
import com.parking.exception.VehicleAlreadyParkedException;
import com.parking.mapper.ParkVehicleMapper;
import com.parking.model.ParkingInfo;
import com.parking.model.ParkingStatus;
import com.parking.service.ParkVehicleService;
import com.parking.storage.ParkingStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.OptionalInt;

@Service
@RequiredArgsConstructor
public class ParkVehicleServiceImpl implements ParkVehicleService {

    private final ParkingStorage parkingStorage;
    private final ParkVehicleMapper parkVehicleMapper;

    @Override
    public ParkingInfo parkVehicle(ParkVehicleRequestDTO request) {
        if (parkingStorage.isVehicleParked(request.getVehicleReg())) {
            throw new VehicleAlreadyParkedException("A vehicle with this number is already parked:  " + request.getVehicleReg());
        }

        OptionalInt availableSpace = parkingStorage.findFirstAvailableSpace();
        if (availableSpace.isEmpty()) {
            throw new NoAvailableParkingSpacesException("No available parking spaces");
        }

        LocalDateTime timeIn = LocalDateTime.now();
        ParkingInfo parkingInfo = parkVehicleMapper.toParkingInfo(request, availableSpace.getAsInt(), timeIn);

        parkingStorage.parkVehicle(parkingInfo);

        return parkingInfo;
    }

    @Override
    public ParkingStatus getParkingStatus() {
        return new ParkingStatus(
                parkingStorage.getAvailableSpaces(),
                parkingStorage.getOccupiedSpaces()
        );
    }
}


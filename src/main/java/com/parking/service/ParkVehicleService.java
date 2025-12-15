package com.parking.service;

import com.parking.dto.ParkVehicleRequestDTO;
import com.parking.dto.ParkVehicleResponseDTO;
import com.parking.dto.ParkingStatusResponseDTO;
import org.jspecify.annotations.Nullable;

public interface ParkVehicleService {
    ParkVehicleResponseDTO parkVehicle(ParkVehicleRequestDTO request);

    ParkingStatusResponseDTO getParkingStatus();

}


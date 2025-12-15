package com.parking.service;

import com.parking.dto.ParkVehicleRequestDTO;
import com.parking.model.ParkingInfo;
import com.parking.model.ParkingStatus;
import org.jspecify.annotations.Nullable;

public interface ParkVehicleService {
    ParkingInfo parkVehicle(ParkVehicleRequestDTO request);

    ParkingStatus getParkingStatus();

}


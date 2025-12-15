package com.parking.controller;

import com.parking.api.ParkingApi;
import com.parking.dto.ParkVehicleRequestDTO;
import com.parking.dto.ParkVehicleResponseDTO;
import com.parking.dto.ParkingStatusResponseDTO;
import com.parking.service.ParkVehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParkingController implements ParkingApi {

    private final ParkVehicleService parkVehicleService;

    @Override
    public ResponseEntity<ParkingStatusResponseDTO> getParkingStatus() {
        return ResponseEntity.ok(parkVehicleService.getParkingStatus());
    }

    @Override
    public ResponseEntity<ParkVehicleResponseDTO> parkVehicle(@Valid ParkVehicleRequestDTO parkVehicleRequest) {
        return ResponseEntity.status(201).body(parkVehicleService.parkVehicle(parkVehicleRequest));
    }
}


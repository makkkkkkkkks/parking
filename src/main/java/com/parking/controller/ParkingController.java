package com.parking.controller;

import com.parking.api.ParkingApi;
import com.parking.dto.ParkVehicleRequestDTO;
import com.parking.dto.ParkVehicleResponseDTO;
import com.parking.dto.ParkingStatusResponseDTO;
import com.parking.mapper.ParkVehicleMapper;
import com.parking.mapper.ParkingStatusMapper;
import com.parking.service.ParkVehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParkingController implements ParkingApi {

    private final ParkVehicleService parkVehicleService;
    private final ParkVehicleMapper parkVehicleMapper;
    private final ParkingStatusMapper parkingStatusMapper;

    @Override
    public ResponseEntity<ParkingStatusResponseDTO> getParkingStatus() {
        var parkingStatus = parkVehicleService.getParkingStatus();
        var responseDTO = parkingStatusMapper.toParkingStatusResponseDTO(parkingStatus);
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<ParkVehicleResponseDTO> parkVehicle(@Valid ParkVehicleRequestDTO parkVehicleRequest) {
        var parkVehicle = parkVehicleService.parkVehicle(parkVehicleRequest);
        var responseDTO = parkVehicleMapper.toParkVehicleResponseDTO(parkVehicle);
        return ResponseEntity.ok(responseDTO);
    }
}


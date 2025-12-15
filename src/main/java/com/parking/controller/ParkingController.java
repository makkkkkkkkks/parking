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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ParkingController implements ParkingApi {

    private final ParkVehicleService parkVehicleService;
    private final ParkVehicleMapper parkVehicleMapper;
    private final ParkingStatusMapper parkingStatusMapper;

    @Override
    public ResponseEntity<ParkingStatusResponseDTO> getParkingStatus() {
        log.info("Request received to get parking status");
        var parkingStatus = parkVehicleService.getParkingStatus();
        var responseDTO = parkingStatusMapper.toParkingStatusResponseDTO(parkingStatus);
        log.info("Parking status retrieved successfully: availableSpaces={}, occupiedSpaces={}", 
                responseDTO.getAvailableSpaces(), responseDTO.getOccupiedSpaces());
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<ParkVehicleResponseDTO> parkVehicle(@Valid ParkVehicleRequestDTO parkVehicleRequest) {
        log.info("Request received to park vehicle with registration number: {}", parkVehicleRequest.getVehicleReg());
        var parkVehicle = parkVehicleService.parkVehicle(parkVehicleRequest);
        var responseDTO = parkVehicleMapper.toParkVehicleResponseDTO(parkVehicle);
        log.info("Vehicle parked successfully: vehicleReg={}, spaceNumber={}", 
                responseDTO.getVehicleReg(), responseDTO.getSpaceNumber());
        return ResponseEntity.status(201).body(responseDTO);
    }
}

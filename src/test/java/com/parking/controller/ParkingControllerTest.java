package com.parking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.dto.ParkVehicleRequestDTO;
import com.parking.dto.ParkVehicleResponseDTO;
import com.parking.dto.ParkingStatusResponseDTO;
import com.parking.exception.NoAvailableParkingSpacesException;
import com.parking.exception.VehicleAlreadyParkedException;
import com.parking.mapper.ParkVehicleMapper;
import com.parking.mapper.ParkingStatusMapper;
import com.parking.model.ParkingInfo;
import com.parking.model.ParkingStatus;
import com.parking.service.ParkVehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParkingController.class)
class ParkingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ParkVehicleService parkVehicleService;

    @MockBean
    private ParkVehicleMapper parkVehicleMapper;

    @MockBean
    private ParkingStatusMapper parkingStatusMapper;

    private ParkVehicleRequestDTO validRequest;
    private ParkingInfo parkingInfo;
    private ParkVehicleResponseDTO parkVehicleResponse;
    private ParkingStatus parkingStatus;
    private ParkingStatusResponseDTO parkingStatusResponse;

    @BeforeEach
    void setUp() {
        validRequest = new ParkVehicleRequestDTO();
        validRequest.setVehicleReg("ABC123");
        validRequest.setVehicleType(1);

        parkingInfo = new ParkingInfo();
        parkingInfo.setVehicleReg("ABC123");
        parkingInfo.setVehicleType(1);
        parkingInfo.setSpaceNumber(5);
        parkingInfo.setTimeIn(LocalDateTime.now());

        parkVehicleResponse = new ParkVehicleResponseDTO();
        parkVehicleResponse.setVehicleReg("ABC123");
        parkVehicleResponse.setSpaceNumber(5);
        parkVehicleResponse.setTimeIn(LocalDateTime.now().atOffset(ZoneOffset.UTC));

        parkingStatus = new ParkingStatus(10, 5);
        
        parkingStatusResponse = new ParkingStatusResponseDTO();
        parkingStatusResponse.setAvailableSpaces(10);
        parkingStatusResponse.setOccupiedSpaces(5);
    }

    @Test
    void getParkingStatus_ValidRequest_ReturnsOk() throws Exception {
        // Given
        when(parkVehicleService.getParkingStatus())
                .thenReturn(parkingStatus);
        when(parkingStatusMapper.toParkingStatusResponseDTO(any(ParkingStatus.class)))
                .thenReturn(parkingStatusResponse);

        // When & Then
        mockMvc.perform(get("/parking")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.availableSpaces").value(10))
                .andExpect(jsonPath("$.occupiedSpaces").value(5));
    }

    @Test
    void parkVehicle_ValidRequest_ReturnsCreated() throws Exception {
        // Given
        when(parkVehicleService.parkVehicle(any(ParkVehicleRequestDTO.class)))
                .thenReturn(parkingInfo);
        when(parkVehicleMapper.toParkVehicleResponseDTO(any(ParkingInfo.class)))
                .thenReturn(parkVehicleResponse);

        // When & Then
        mockMvc.perform(post("/parking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.vehicleReg").value("ABC123"))
                .andExpect(jsonPath("$.spaceNumber").value(5))
                .andExpect(jsonPath("$.timeIn").exists());
    }

    @Test
    void parkVehicle_VehicleAlreadyParked_ReturnsFound() throws Exception {
        // Given
        when(parkVehicleService.parkVehicle(any(ParkVehicleRequestDTO.class)))
                .thenThrow(new VehicleAlreadyParkedException("Vehicle ABC123 is already parked"));

        // When & Then
        mockMvc.perform(post("/parking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isFound())
                .andExpect(content().string("Vehicle ABC123 is already parked"));
    }

    @Test
    void parkVehicle_NoAvailableSpaces_ReturnsFound() throws Exception {
        // Given
        when(parkVehicleService.parkVehicle(any(ParkVehicleRequestDTO.class)))
                .thenThrow(new NoAvailableParkingSpacesException("No available parking spaces"));

        // When & Then
        mockMvc.perform(post("/parking")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isFound())
                .andExpect(content().string("No available parking spaces"));
    }
}



package com.parking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.dto.BillRequestDTO;
import com.parking.dto.BillResponseDTO;
import com.parking.exception.CarNotFoundException;
import com.parking.service.BillService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BillController.class)
class BillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BillService billService;

    private BillRequestDTO validRequest;
    private BillResponseDTO validResponse;

    @BeforeEach
    void setUp() {
        validRequest = new BillRequestDTO();
        validRequest.setVehicleReg("ABC123");

        validResponse = new BillResponseDTO();
        validResponse.setBillId("bill-123");
        validResponse.setVehicleReg("ABC123");
        validResponse.setVehicleCharge(15.50);
        validResponse.setTimeIn(LocalDateTime.now().minusHours(2).atOffset(ZoneOffset.UTC));
        validResponse.setTimeOut(LocalDateTime.now().atOffset(ZoneOffset.UTC));
    }

    @Test
    void generateBill_ValidRequest_ReturnsOk() throws Exception {
        // Given
        when(billService.generateBill(any(BillRequestDTO.class)))
                .thenReturn(validResponse);

        // When & Then
        mockMvc.perform(post("/parking/bill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.billId").value("bill-123"))
                .andExpect(jsonPath("$.vehicleReg").value("ABC123"))
                .andExpect(jsonPath("$.vehicleCharge").value(15.50))
                .andExpect(jsonPath("$.timeIn").exists())
                .andExpect(jsonPath("$.timeOut").exists());
    }

    @Test
    void generateBill_VehicleNotFound_ReturnsNotFound() throws Exception {
        // Given
        BillRequestDTO request = new BillRequestDTO();
        request.setVehicleReg("NOTFOUND");

        when(billService.generateBill(any(BillRequestDTO.class)))
                .thenThrow(new CarNotFoundException("Vehicle not found: NOTFOUND"));

        // When & Then
        mockMvc.perform(post("/parking/bill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Vehicle not found: NOTFOUND"));
    }

    @Test
    void generateBill_MinimumParkingTime_ReturnsMinimumCharge() throws Exception {
        BillResponseDTO minChargeResponse = new BillResponseDTO();
        minChargeResponse.setBillId("bill-min");
        minChargeResponse.setVehicleReg("MIN123");
        minChargeResponse.setVehicleCharge(5.00);
        minChargeResponse.setTimeIn(LocalDateTime.now().minusMinutes(1).atOffset(ZoneOffset.UTC));
        minChargeResponse.setTimeOut(LocalDateTime.now().atOffset(ZoneOffset.UTC));

        BillRequestDTO request = new BillRequestDTO();
        request.setVehicleReg("MIN123");

        when(billService.generateBill(any(BillRequestDTO.class)))
                .thenReturn(minChargeResponse);

        // When & Then
        mockMvc.perform(post("/parking/bill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleReg").value("MIN123"))
                .andExpect(jsonPath("$.vehicleCharge").value(5.00));
    }
}


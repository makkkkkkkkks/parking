package com.parking.controller;

import com.parking.api.BillApi;
import com.parking.dto.BillRequestDTO;
import com.parking.dto.BillResponseDTO;
import com.parking.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BillController implements BillApi {

    private final BillService billService;

    @Override
    public ResponseEntity<BillResponseDTO> generateBill(@Valid BillRequestDTO billRequestDTO) {
        BillResponseDTO bill = billService.generateBill(billRequestDTO);
        log.info("Generated bill for vehicle with registration number: {}, total charge: {}", 
                bill.getVehicleReg(), bill.getVehicleCharge());
        return ResponseEntity.ok(bill);
    }
}


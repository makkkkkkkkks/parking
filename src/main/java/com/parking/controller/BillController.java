package com.parking.controller;

import com.parking.api.BillApi;
import com.parking.dto.GenerateBillRequestDTO;
import com.parking.dto.BillResponseDTO;
import com.parking.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BillController implements BillApi {

    private final BillService billService;

    @Override
    public ResponseEntity<BillResponseDTO> generateBill(@Valid GenerateBillRequestDTO generateBillRequest) {
        return ResponseEntity.ok(billService.generateBill(generateBillRequest));
    }
}


package com.parking.service;

import com.parking.dto.GenerateBillRequestDTO;
import com.parking.dto.BillResponseDTO;

public interface BillService {
    BillResponseDTO generateBill(GenerateBillRequestDTO request);
}


package com.parking.service;

import com.parking.dto.BillRequestDTO;
import com.parking.dto.BillResponseDTO;

public interface BillService {
    BillResponseDTO generateBill(BillRequestDTO request);
}


package com.parking.service.impl;

import com.parking.dto.GenerateBillRequestDTO;
import com.parking.dto.BillResponseDTO;
import com.parking.service.BillService;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl implements BillService {
    @Override
    public BillResponseDTO generateBill(GenerateBillRequestDTO request) {
        return null;
    }
}


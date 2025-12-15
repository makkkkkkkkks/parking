package com.parking.service.impl;

import com.parking.config.ParkingBillingProperties;
import com.parking.dto.GenerateBillRequestDTO;
import com.parking.dto.BillResponseDTO;
import com.parking.mapper.BillMapper;
import com.parking.model.ParkingInfo;
import com.parking.service.BillService;
import com.parking.storage.ParkingStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {

    private final ParkingStorage parkingStorage;
    private final ParkingBillingProperties billingProperties;
    private final BillMapper billMapper;

    @Override
    public BillResponseDTO generateBill(GenerateBillRequestDTO request) {
        ParkingInfo parkingInfo = validateAndGetParkingInfo(request.getVehicleReg());
        LocalDateTime timeOut = LocalDateTime.now();
        long minutesParked = calculateParkingMinutes(parkingInfo.getTimeIn(), timeOut);
        BigDecimal totalCharge = calculateTotalCharge(parkingInfo.getVehicleType(), minutesParked);
        
        parkingStorage.removeVehicle(request.getVehicleReg());
        
        return billMapper.toBillResponseDTO(parkingInfo, timeOut, UUID.randomUUID().toString(), totalCharge);
    }

    private ParkingInfo validateAndGetParkingInfo(String vehicleReg) {
        ParkingInfo parkingInfo = parkingStorage.getByVehicleReg(vehicleReg);
        if (parkingInfo == null) {
            throw new IllegalArgumentException("Vehicle not found: " + vehicleReg);
        }
        return parkingInfo;
    }

    private long calculateParkingMinutes(LocalDateTime timeIn, LocalDateTime timeOut) {
        long minutes = Duration.between(timeIn, timeOut).toMinutes();
        return Math.max(minutes, 0);
    }

    private BigDecimal calculateTotalCharge(int vehicleType, long minutesParked) {
        BigDecimal baseCharge = calculateBaseCharge(vehicleType, minutesParked);
        BigDecimal additionalCharge = calculateAdditionalCharge(minutesParked);
        return baseCharge.add(additionalCharge).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateBaseCharge(int vehicleType, long minutesParked) {
        BigDecimal baseRate = getBaseRate(vehicleType);
        return baseRate.multiply(BigDecimal.valueOf(minutesParked));
    }

    private BigDecimal calculateAdditionalCharge(long minutesParked) {
        long periods = minutesParked / billingProperties.getAdditionalCharge().getPeriodMinutes();
        return billingProperties.getAdditionalCharge().getAmount()
                .multiply(BigDecimal.valueOf(periods));
    }

    private BigDecimal getBaseRate(int vehicleType) {
        return switch (vehicleType) {
            case 1 -> billingProperties.getRate().getSmallCar();
            case 2 -> billingProperties.getRate().getMediumCar();
            case 3 -> billingProperties.getRate().getLargeCar();
            default -> throw new IllegalArgumentException("Invalid vehicle type: " + vehicleType);
        };
    }
}


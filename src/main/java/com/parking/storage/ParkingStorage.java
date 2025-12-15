package com.parking.storage;

import com.parking.model.ParkingInfo;
import com.parking.repository.ParkingInfoRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.OptionalInt;

@Getter
@Component
@RequiredArgsConstructor
public class ParkingStorage {

    @Value("${parking.total-spaces:10}")
    private final int totalSpaces;
    private final ParkingInfoRepository repository;

    public int getOccupiedSpaces() {
        return (int) repository.count();
    }

    public int getAvailableSpaces() {
        return totalSpaces - getOccupiedSpaces();
    }

    public ParkingInfo getByVehicleReg(String vehicleReg) {
        return repository.findByVehicleReg(vehicleReg).orElse(null);
    }

    public ParkingInfo getBySpaceNumber(int spaceNumber) {
        return repository.findBySpaceNumber(spaceNumber).orElse(null);
    }

    public OptionalInt findFirstAvailableSpace() {
        for (int i = 1; i <= totalSpaces; i++) {
            if (!repository.existsBySpaceNumber(i)) {
                return OptionalInt.of(i);
            }
        }
        return OptionalInt.empty();
    }

    public void parkVehicle(ParkingInfo parkingInfo) {
        repository.save(parkingInfo);
    }

    public ParkingInfo removeVehicle(String vehicleReg) {
        ParkingInfo parkingInfo = repository.findByVehicleReg(vehicleReg)
                .orElse(null);

        if (parkingInfo != null) {
            repository.delete(parkingInfo);
        }

        return parkingInfo;
    }

    public boolean isVehicleParked(String vehicleReg) {
        return repository.existsByVehicleReg(vehicleReg);
    }

    public boolean isSpaceOccupied(int spaceNumber) {
        return repository.existsBySpaceNumber(spaceNumber);
    }
}

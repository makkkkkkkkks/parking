package com.parking.repository;

import com.parking.model.ParkingInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ParkingInfoRepositoryImpl implements ParkingInfoRepository {

    private final Map<String, ParkingInfo> storage = new HashMap<>();

    @Override
    public Optional<ParkingInfo> findByVehicleReg(String vehicleReg) {
        return Optional.ofNullable(storage.get(vehicleReg));
    }

    @Override
    public Optional<ParkingInfo> findBySpaceNumber(int spaceNumber) {
        return storage.values().stream()
                .filter(p -> p.getSpaceNumber() == spaceNumber)
                .findFirst();
    }

    @Override
    public boolean existsByVehicleReg(String vehicleReg) {
        return storage.containsKey(vehicleReg);
    }

    @Override
    public boolean existsBySpaceNumber(int spaceNumber) {
        return storage.values().stream()
                .anyMatch(p -> p.getSpaceNumber() == spaceNumber);
    }

    @Override
    public long count() {
        return storage.size();
    }

    @Override
    public void save(ParkingInfo info) {
        storage.put(info.getVehicleReg(), info);
    }

    @Override
    public void delete(ParkingInfo info) {
        storage.remove(info.getVehicleReg());
    }
}


package com.parking.repository;

import com.parking.model.ParkingInfo;

import java.util.Optional;

public interface ParkingInfoRepository {
    Optional<ParkingInfo> findByVehicleReg(String vehicleReg);

    Optional<ParkingInfo> findBySpaceNumber(int spaceNumber);

    boolean existsByVehicleReg(String vehicleReg);

    boolean existsBySpaceNumber(int spaceNumber);

    long count();

    void save(ParkingInfo info);

    void delete(ParkingInfo info);
}

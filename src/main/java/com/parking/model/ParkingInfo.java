package com.parking.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ParkingInfo {

    private String vehicleReg;
    private int vehicleType;
    private int spaceNumber;
    private LocalDateTime timeIn;

}


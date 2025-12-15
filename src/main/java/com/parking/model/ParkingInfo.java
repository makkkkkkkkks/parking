package com.parking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "parking_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingInfo {
    @Id
    @Column(name = "vehicle_reg", unique = true)
    private String vehicleReg;
    
    @Column(name = "vehicle_type", nullable = false)
    private int vehicleType;
    
    @Column(name = "space_number", unique = true, nullable = false)
    private int spaceNumber;
    
    @Column(name = "time_in", nullable = false)
    private LocalDateTime timeIn;
}


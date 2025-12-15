package com.parking.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingStatus {
    private int availableSpaces;
    private int occupiedSpaces;
}

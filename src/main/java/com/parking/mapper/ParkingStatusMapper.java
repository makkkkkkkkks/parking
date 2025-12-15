package com.parking.mapper;

import com.parking.dto.ParkingStatusResponseDTO;
import com.parking.model.ParkingStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParkingStatusMapper {

    ParkingStatusResponseDTO toParkingStatusResponseDTO(ParkingStatus parkingStatus);
}


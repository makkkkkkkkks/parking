package com.parking.mapper;

import com.parking.dto.ParkingStatusResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParkingStatusMapper {

    @Mapping(target = "availableSpaces", expression = "java(availableSpaces)")
    @Mapping(target = "occupiedSpaces", expression = "java(occupiedSpaces)")
    ParkingStatusResponseDTO toParkingStatusResponseDTO(int availableSpaces, int occupiedSpaces);
}


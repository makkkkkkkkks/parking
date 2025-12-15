package com.parking.mapper;

import com.parking.dto.ParkVehicleRequestDTO;
import com.parking.dto.ParkVehicleResponseDTO;
import com.parking.model.ParkingInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface ParkVehicleMapper {

    @Mapping(target = "spaceNumber", source = "spaceNumber")
    @Mapping(target = "timeIn", source = "timeIn")
    ParkingInfo toParkingInfo(ParkVehicleRequestDTO request, int spaceNumber, LocalDateTime timeIn);

    @Mapping(target = "timeIn", source = "parkingInfo.timeIn", qualifiedByName = "localDateTimeToOffsetDateTime")
    ParkVehicleResponseDTO toParkVehicleResponseDTO(ParkingInfo parkingInfo);

    @Named("localDateTimeToOffsetDateTime")
    default OffsetDateTime localDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
    }
}


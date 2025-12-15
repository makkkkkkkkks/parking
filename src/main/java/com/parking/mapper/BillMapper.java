package com.parking.mapper;

import com.parking.dto.BillResponseDTO;
import com.parking.model.ParkingInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface BillMapper {

    @Mapping(target = "billId", ignore = true)
    @Mapping(target = "vehicleCharge", ignore = true)
    @Mapping(target = "timeIn", source = "parkingInfo.timeIn", qualifiedByName = "localDateTimeToOffsetDateTime")
    @Mapping(target = "timeOut", source = "timeOut", qualifiedByName = "localDateTimeToOffsetDateTime")
    BillResponseDTO toBillResponseDTO(ParkingInfo parkingInfo, LocalDateTime timeOut);

    default BillResponseDTO toBillResponseDTO(ParkingInfo parkingInfo, LocalDateTime timeOut, String billId, BigDecimal vehicleCharge) {
        BillResponseDTO dto = toBillResponseDTO(parkingInfo, timeOut);
        dto.setBillId(billId);
        dto.setVehicleCharge(vehicleCharge.doubleValue());
        return dto;
    }

    @Named("localDateTimeToOffsetDateTime")
    default OffsetDateTime localDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
    }
}


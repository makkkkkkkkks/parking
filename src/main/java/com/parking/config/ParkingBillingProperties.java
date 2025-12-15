package com.parking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Data
@Configuration
@ConfigurationProperties(prefix = "parking")
public class ParkingBillingProperties {

    private Rate rate;
    private AdditionalCharge additionalCharge;

    @Data
    public static class Rate {
        private BigDecimal smallCar;
        private BigDecimal mediumCar;
        private BigDecimal largeCar;
    }

    @Data
    public static class AdditionalCharge {
        private BigDecimal amount;
        private Integer periodMinutes;
    }
}


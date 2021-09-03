package com.main.sellit.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceProvider {
    String uuid,
            avgPunctualityRating,
            avgCommunicationRating,
            avgPriceRating,
            overallRating,
            billingRatePerHour,
            experienceInMonths,
            providerName,
            providerEmail;

}

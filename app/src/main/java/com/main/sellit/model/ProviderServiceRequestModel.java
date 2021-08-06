package com.main.sellit.model;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProviderServiceRequestModel {
    String uuid,
            requestDescription,
            requiredDate,
            expectedStartTime,
            requestBy,
            email,
            locationCity,
            country;
    Long expectedHours;

    @NonNull
    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, ProviderServiceRequestModel.class);

    }
}

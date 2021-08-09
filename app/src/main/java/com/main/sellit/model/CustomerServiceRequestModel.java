package com.main.sellit.model;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerServiceRequestModel {
    String uuid,
            requirementDescription,
            requiredOn,
            expectedStartTime,
            expectedHours;

    @NonNull
    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, CustomerServiceRequestModel.class);
    }
}

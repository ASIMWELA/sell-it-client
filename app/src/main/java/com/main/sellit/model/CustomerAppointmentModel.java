package com.main.sellit.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAppointmentModel {
    String uuid, appointmentDate,
    appointmentStartTime,
    appointmentEndTime,
    appointmentDesc,
    appointmentWith,
    providerPhone,
    providerEmail;
}

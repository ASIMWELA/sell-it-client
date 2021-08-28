package com.main.sellit.model;

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
public class ProviderAppointmentModel {
    String uuid,
            appointmentDate,
            appointmentStartTime,
            appointmentEndTime,
            appointmentDesc,
            appointmentWith,
            customerPhone,
            customerEmail;
}

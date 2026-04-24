package com.robert.appointmentbooking.logic;

import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

@Builder
public record AppointmentCreationTo(@NotNull Instant startDateTime, @NotNull Instant endDateTime,
		@NotNull String status, @NotNull Long clientId, @NotNull Long treatmentId) {
}

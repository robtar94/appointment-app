package com.capgemini.training.appointmentbooking.logic;

import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import lombok.Builder;

@Builder
public record AppointmentBookingEto(@NotNull Instant dateTime, @NotNull Instant endDateTime, @NotNull Long clientId,
		@NotNull Long treatmentId) {
}

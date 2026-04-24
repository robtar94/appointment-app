package com.robert.appointmentbooking.logic;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record TreatmentCreationTo(@NotNull @Size(min = 5, max = 20) String name,
		@NotNull @Size(min = 5, max = 80) String description, @Min(1) int durationMinutes, @NotNull Long specialistId) {
}

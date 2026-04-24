package com.capgemini.training.appointmentbooking.common.to;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TreatmentEto {
	private final Long id;
	private final String name;
	private final String description;
	private final int durationInMinutes;
}

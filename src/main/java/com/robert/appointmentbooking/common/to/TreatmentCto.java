package com.robert.appointmentbooking.common.to;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TreatmentCto {
	private final TreatmentEto treatmentEto;
	private final SpecialistEto specialistEto;
}

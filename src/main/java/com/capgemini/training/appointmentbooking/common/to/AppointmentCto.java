package com.capgemini.training.appointmentbooking.common.to;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppointmentCto {
	private final AppointmentEto appointmentEto;
	private final ClientEto clientEto;
	private final TreatmentCto treatmentCto;
}

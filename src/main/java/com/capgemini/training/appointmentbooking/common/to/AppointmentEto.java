package com.capgemini.training.appointmentbooking.common.to;

import com.capgemini.training.appointmentbooking.common.datatype.AppointmentStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AppointmentEto {
	private final Long id;
	private final Instant dateTime;
	private final Instant endDateTime;
	private final AppointmentStatus status;
}

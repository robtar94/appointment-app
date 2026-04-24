package com.robert.appointmentbooking.common.to;

import com.robert.appointmentbooking.common.datatype.Specialization;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpecialistEto {
	private final Long id;
	private final Specialization specialization;
}

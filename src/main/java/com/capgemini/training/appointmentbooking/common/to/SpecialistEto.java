package com.capgemini.training.appointmentbooking.common.to;

import com.capgemini.training.appointmentbooking.common.datatype.Specialization;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SpecialistEto {
	private final Long id;
	private final Specialization specialization;
}

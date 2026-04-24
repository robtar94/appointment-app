package com.robert.appointmentbooking.service.mapper;

import java.util.Optional;

import com.robert.appointmentbooking.common.to.SpecialistEto;
import com.robert.appointmentbooking.common.to.TreatmentCto;
import com.robert.appointmentbooking.common.to.TreatmentEto;
import com.robert.appointmentbooking.logic.TreatmentCreationTo;
import com.robert.appointmentbooking.service.model.Treatment;
import com.robert.appointmentbooking.service.model.TreatmentDetails;
import com.robert.appointmentbooking.service.model.TreatmentDetailsAllOfSpecialist;
import com.robert.appointmentbooking.service.model.TreatmentRequest;

public class TreatmentApiMapper {

	public TreatmentCreationTo toCreationTo(TreatmentRequest request) {
		return TreatmentCreationTo.builder().name(request.getName().orElse(null))
				.durationMinutes(request.getDuration().orElse(0)).specialistId(request.getSpecialistId().orElse(null))
				.description("Default description").build();
	}

	public Treatment toApiTreatment(TreatmentCto cto) {
		TreatmentEto eto = cto.getTreatmentEto();
		SpecialistEto specialist = cto.getSpecialistEto();

		Treatment result = new Treatment();
		result.setId(Optional.ofNullable(eto.getId()));
		result.setName(Optional.ofNullable(eto.getName()));
		result.setDuration(Optional.of(eto.getDurationInMinutes()));
		result.setSpecialistId(Optional.ofNullable(specialist.getId()));
		return result;
	}

	public TreatmentDetails toApiTreatmentDetails(TreatmentCto cto) {
		TreatmentEto eto = cto.getTreatmentEto();
		SpecialistEto specialist = cto.getSpecialistEto();

		TreatmentDetails result = new TreatmentDetails();
		result.setId(Optional.ofNullable(eto.getId()));
		result.setName(Optional.ofNullable(eto.getName()));
		result.setDuration(Optional.of(eto.getDurationInMinutes()));
		result.setSpecialistId(Optional.ofNullable(specialist.getId()));

		TreatmentDetailsAllOfSpecialist specialistDto = new TreatmentDetailsAllOfSpecialist();
		specialistDto.setId(Optional.ofNullable(specialist.getId()));
		specialistDto.setName(Optional.of(specialist.getSpecialization().name()));

		result.setSpecialist(Optional.of(specialistDto));
		return result;
	}

}

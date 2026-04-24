package com.capgemini.training.appointmentbooking.service.mapper;
import java.util.Optional;

import com.capgemini.training.appointmentbooking.common.to.AppointmentCto;
import com.capgemini.training.appointmentbooking.common.to.AppointmentEto;
import com.capgemini.training.appointmentbooking.logic.AppointmentBookingEto;
import com.capgemini.training.appointmentbooking.service.model.Appointment;
import com.capgemini.training.appointmentbooking.service.model.AppointmentRequest;

public class AppointmentApiMapper {

	public AppointmentBookingEto toBookingEto(AppointmentRequest request) {
		return AppointmentBookingEto.builder().clientId(request.getClientId()).treatmentId(request.getTreatmentId())
				.dateTime(request.getDateTime().toInstant()).build();
	}

	public Appointment toApiAppointment(AppointmentCto cto) {
		AppointmentEto appointmentEto = cto.getAppointmentEto();

		Appointment result = new Appointment();
		result.setId(Optional.of(appointmentEto.getId()));
		result.setClientId(Optional.of(cto.getClientEto().getId()));
		result.setTreatmentId(Optional.of(cto.getTreatmentCto().getTreatmentEto().getId()));
		result.setDateTime(Optional.of(java.util.Date.from(appointmentEto.getDateTime())));
		result.setStatus(Optional.of(Appointment.StatusEnum.valueOf(appointmentEto.getStatus().name())));
		return result;
	}

}
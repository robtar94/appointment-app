package com.robert.appointmentbooking.service.mapper;
import java.util.Optional;

import com.robert.appointmentbooking.common.to.AppointmentCto;
import com.robert.appointmentbooking.common.to.AppointmentEto;
import com.robert.appointmentbooking.logic.AppointmentBookingEto;
import com.robert.appointmentbooking.service.model.Appointment;
import com.robert.appointmentbooking.service.model.AppointmentRequest;

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
package com.capgemini.training.appointmentbooking.service.impl;

import com.capgemini.training.appointmentbooking.common.datatype.AppointmentStatus;
import com.capgemini.training.appointmentbooking.common.to.AppointmentCto;
import com.capgemini.training.appointmentbooking.dataaccess.criteria.AppointmentCriteria;
import com.capgemini.training.appointmentbooking.logic.AppointmentBookingEto;
import com.capgemini.training.appointmentbooking.logic.FindAppointmentUc;
import com.capgemini.training.appointmentbooking.logic.ManageAppointmentUc;
import com.capgemini.training.appointmentbooking.service.api.AppointmentsApi;
import com.capgemini.training.appointmentbooking.service.mapper.AppointmentApiMapper;
import com.capgemini.training.appointmentbooking.service.model.Appointment;
import com.capgemini.training.appointmentbooking.service.model.AppointmentRequest;
import com.capgemini.training.appointmentbooking.service.model.AppointmentStatusUpdate;
import com.capgemini.training.appointmentbooking.service.model.CheckAvailability200Response;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AppointmentsApiController implements AppointmentsApi {

	private final FindAppointmentUc findAppointmentUc;
	private final ManageAppointmentUc manageAppointmentUc;
	private final AppointmentApiMapper appointmentMapper;

	@Override
	public ResponseEntity<Appointment> createAppointment(@Valid AppointmentRequest appointmentRequest) {

		AppointmentBookingEto bookingEto = appointmentMapper.toBookingEto(appointmentRequest);

		AppointmentCto booked = manageAppointmentUc.bookAppointment(bookingEto);

		return ResponseEntity.status(HttpStatus.CREATED).body(appointmentMapper.toApiAppointment(booked));

	}

	@Override
	public ResponseEntity<List<Appointment>> getAppointments(@Valid Optional<String> clientId,
			@Valid Optional<String> specialistId, @Valid Optional<String> status) {

		Long clientIdValue = clientId.map(Long::valueOf).orElse(null);

		AppointmentStatus statusValue = status.map(AppointmentStatus::valueOf).orElse(null);

		AppointmentCriteria criteria = new AppointmentCriteria(null, //
				null, //
				statusValue, //
				clientIdValue//
		);

		List<Appointment> result = findAppointmentUc.findByCriteria(criteria)//
				.stream()//
				.map(appointmentMapper::toApiAppointment)//
				.toList();//

		return ResponseEntity.ok(result);
	}

	@Override
	public ResponseEntity<Void> updateAppointmentStatus(String appointmentId,
			@Valid AppointmentStatusUpdate appointmentStatusUpdate) {

		Long id = Long.valueOf(appointmentId);

		AppointmentStatus status = AppointmentStatus.valueOf(appointmentStatusUpdate.getStatus().name());

		manageAppointmentUc.updateAppointmentStatus(id, status);

		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<CheckAvailability200Response> checkAvailability(@NotNull @Valid String specialistId,
			@NotNull @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date dateTime) {

		Long specialistIdValue = Long.valueOf(specialistId);
		Instant start = dateTime.toInstant();
		Instant end = start;

		boolean hasConflict = findAppointmentUc.hasConflictingAppointment(specialistIdValue, start, end);

		CheckAvailability200Response response = new CheckAvailability200Response();
		response.setAvailable(Optional.of(!hasConflict));

		return ResponseEntity.ok(response);

	}
}
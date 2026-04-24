package com.robert.appointmentbooking.logic.impl;

import com.robert.appointmentbooking.common.datatype.AppointmentStatus;
import com.robert.appointmentbooking.common.to.AppointmentCto;
import com.robert.appointmentbooking.common.to.AppointmentEto;
import com.robert.appointmentbooking.dataaccess.entity.AppointmentEntity;
import com.robert.appointmentbooking.dataaccess.repository.AppointmentRepository;
import com.robert.appointmentbooking.dataaccess.repository.ClientRepository;
import com.robert.appointmentbooking.dataaccess.repository.TreatmentRepository;
import com.robert.appointmentbooking.logic.AppointmentBookingEto;
import com.robert.appointmentbooking.logic.ManageAppointmentUc;
import com.robert.appointmentbooking.logic.mapper.AppointmentMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
@AllArgsConstructor
public class ManageAppointmentUcImpl implements ManageAppointmentUc {

	private final AppointmentRepository appointmentRepository;

	private final AppointmentMapper appointmentMapper;

	private final ClientRepository clientRepository;

	private final TreatmentRepository treatmentRepository;

	@Override
	public AppointmentCto bookAppointment(AppointmentBookingEto appointmentBookingEto) {
		var treatment = this.treatmentRepository.findById(appointmentBookingEto.treatmentId())
				.orElseThrow(() -> new RuntimeException("Treatment not found"));

		Instant startDateTime = appointmentBookingEto.dateTime();
		Instant endDateTime = appointmentBookingEto.endDateTime();
		if (endDateTime == null) {
			endDateTime = startDateTime.plus(treatment.getDurationMinutes(), ChronoUnit.MINUTES);
		}

		boolean hasConflict = this.appointmentRepository.hasConflictingAppointments(treatment.getSpecialist().getId(),
				startDateTime, endDateTime);

		if (hasConflict) {
			throw new RuntimeException("Specialist is not available at this time");
		}

		boolean hasClientConflict = this.appointmentRepository
				.hasClientConflictingAppointments(appointmentBookingEto.clientId(), startDateTime, endDateTime);

		if (hasClientConflict) {
			throw new RuntimeException("Client already has an appointment at this time");
		}

		AppointmentEntity appointmentEntity = this.appointmentMapper.toAppointmentEntity(AppointmentBookingEto.builder()
				.clientId(appointmentBookingEto.clientId()).treatmentId(appointmentBookingEto.treatmentId())
				.dateTime(startDateTime).endDateTime(endDateTime).build());
		appointmentEntity.setClient(this.clientRepository.getReferenceById(appointmentBookingEto.clientId()));
		appointmentEntity.setTreatment(treatment);
		appointmentEntity.setStatus(AppointmentStatus.SCHEDULED);
		appointmentEntity = this.appointmentRepository.saveAndFlush(appointmentEntity);
		return this.appointmentMapper.toAppointmentCto(appointmentEntity);
	}

	@Override
	public AppointmentEto updateAppointmentStatus(Long appointmentId, AppointmentStatus appointmentStatus) {
		AppointmentEntity appointmentEntity = this.appointmentRepository.findById(appointmentId)
				.orElseThrow(() -> new RuntimeException("Appointment not found"));
		appointmentEntity.setStatus(appointmentStatus);
		appointmentEntity = this.appointmentRepository.saveAndFlush(appointmentEntity);
		return this.appointmentMapper.toAppointmentEto(appointmentEntity);
	}

}

package com.capgemini.training.appointmentbooking.logic.impl;

import com.capgemini.training.appointmentbooking.common.datatype.AppointmentStatus;
import com.capgemini.training.appointmentbooking.common.to.AppointmentCto;
import com.capgemini.training.appointmentbooking.common.to.AppointmentEto;
import com.capgemini.training.appointmentbooking.dataaccess.entity.AppointmentEntity;
import com.capgemini.training.appointmentbooking.dataaccess.repository.AppointmentRepository;
import com.capgemini.training.appointmentbooking.dataaccess.repository.ClientRepository;
import com.capgemini.training.appointmentbooking.dataaccess.repository.TreatmentRepository;
import com.capgemini.training.appointmentbooking.logic.AppointmentBookingEto;
import com.capgemini.training.appointmentbooking.logic.ManageAppointmentUc;
import com.capgemini.training.appointmentbooking.logic.mapper.AppointmentMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

		boolean hasConflict = this.appointmentRepository.hasConflictingAppointments(treatment.getSpecialist().getId(),
				appointmentBookingEto.dateTime(), appointmentBookingEto.endDateTime());

		if (hasConflict) {
			throw new RuntimeException("Specialist is not available at this time");
		}

		boolean hasClientConflict = this.appointmentRepository.hasClientConflictingAppointments(
				appointmentBookingEto.clientId(), appointmentBookingEto.dateTime(),
				appointmentBookingEto.endDateTime());

		if (hasClientConflict) {
			throw new RuntimeException("Client already has an appointment at this time");
		}

		AppointmentEntity appointmentEntity = this.appointmentMapper.toAppointmentEntity(appointmentBookingEto);
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

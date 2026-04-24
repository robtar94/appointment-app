package com.robert.appointmentbooking.logic.impl;

import com.robert.appointmentbooking.common.to.AppointmentCto;
import com.robert.appointmentbooking.dataaccess.criteria.AppointmentCriteria;
import com.robert.appointmentbooking.dataaccess.repository.AppointmentRepository;
import com.robert.appointmentbooking.logic.FindAppointmentUc;
import com.robert.appointmentbooking.logic.mapper.AppointmentMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class FindAppointmentUcImpl implements FindAppointmentUc {

	private final AppointmentRepository appointmentRepository;

	private final AppointmentMapper appointmentMapper;

	@Override
	public Optional<AppointmentCto> findById(Long id) {
		return this.appointmentRepository.findById(id).map(this.appointmentMapper::toAppointmentCto);
	}

	@Override
	public List<AppointmentCto> findAll() {
		return this.appointmentRepository.findAll().stream().map(this.appointmentMapper::toAppointmentCto).toList();
	}

	@Override
	public List<AppointmentCto> findByCriteria(AppointmentCriteria criteria) {
		return this.appointmentRepository.findByCriteria(criteria).stream()
				.map(this.appointmentMapper::toAppointmentCto).toList();
	}

	@Override
	public boolean hasConflictingAppointment(Long specialistId, Instant dateTime, Instant endDateTime) {
		return this.appointmentRepository.hasConflictingAppointments(specialistId, dateTime, endDateTime);
	}
}

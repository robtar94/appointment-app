package com.robert.appointmentbooking.dataaccess.repository;

import java.time.Instant;
import java.util.List;

import com.robert.appointmentbooking.common.BaseDataJpaTest;
import com.robert.appointmentbooking.common.datatype.AppointmentStatus;
import com.robert.appointmentbooking.dataaccess.criteria.AppointmentHistoryCriteria;
import com.robert.appointmentbooking.dataaccess.entity.AppointmentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AppointmentRepositoryIT extends BaseDataJpaTest {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Test
	void shouldFindAll() {
		// when
		List<AppointmentEntity> result = appointmentRepository.findAll();

		// then
		assertThat(result).isNotEmpty().hasSize(20);
	}

	@Test
	void shouldFindAllPastAppointmentsBelongToCorrectSpecialistBeforeSpecificDate() {
		// given
		Long specialistId = -3L;
		Instant cutoffDate = toInstant("2024-04-01 00:00:00");

		// when
		List<AppointmentEntity> result = appointmentRepository.findPastAppointmentsBySpecialist(specialistId,
				cutoffDate);

		// then
		assertThat(result).hasSize(2).allSatisfy(appointment -> {
			assertThat(appointment.getStatus()).isNotEqualTo(AppointmentStatus.CANCELLED);
			assertThat(appointment.getDateTime()).isBefore(cutoffDate);
			assertThat(appointment.getTreatment().getSpecialist().getId()).isEqualTo(specialistId);
		}).extracting(AppointmentEntity::getId).containsExactly(-10L, -9L); // ordered by dateTime DESC
	}

	@Test
	void shouldfindConflictingAppointments() {
		// given
		final Long specialistId = -3L;
		final Instant appointmentStartTime = toInstant("2024-03-09 09:00:00");
		final Instant appointmentEndTime = toInstant("2024-03-09 11:00:00");

		// when
		final boolean result = appointmentRepository.hasConflictingAppointments(specialistId, appointmentStartTime,
				appointmentEndTime);

		// then
		assertThat(result).isTrue();
	}

	@Test
	void shouldFindAppointmentHistoryByClientId() {
		// given
		AppointmentHistoryCriteria criteria = new AppointmentHistoryCriteria(-1L);

		// when
		List<AppointmentEntity> result = appointmentRepository.findByHistoryCriteria(criteria);

		// then
		assertThat(result).hasSize(5)
				.allSatisfy(appointment -> assertThat(appointment.getClient().getId()).isEqualTo(-1L))
				.extracting(AppointmentEntity::getId).containsExactly(-17L, -13L, -9L, -5L, -1L);
	}

}
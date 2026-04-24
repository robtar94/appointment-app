package com.robert.appointmentbooking.logic.impl;

import com.robert.appointmentbooking.common.BaseTest;
import com.robert.appointmentbooking.common.datatype.AppointmentStatus;
import com.robert.appointmentbooking.common.datatype.Specialization;
import com.robert.appointmentbooking.common.to.AppointmentCto;
import com.robert.appointmentbooking.common.to.AppointmentEto;
import com.robert.appointmentbooking.common.to.SpecialistEto;
import com.robert.appointmentbooking.common.to.TreatmentCto;
import com.robert.appointmentbooking.common.to.TreatmentEto;
import com.robert.appointmentbooking.dataaccess.criteria.AppointmentCriteria;
import com.robert.appointmentbooking.dataaccess.entity.AppointmentEntity;
import com.robert.appointmentbooking.dataaccess.entity.ClientEntity;
import com.robert.appointmentbooking.dataaccess.entity.SpecialistEntity;
import com.robert.appointmentbooking.dataaccess.entity.TreatmentEntity;
import com.robert.appointmentbooking.dataaccess.repository.AppointmentRepository;
import com.robert.appointmentbooking.logic.mapper.AppointmentMapper;
import com.robert.appointmentbooking.logic.mapper.TreatmentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindAppointmentUcImplTest extends BaseTest {

	private static final Long APPOINTMENT_ID = -1L;
	private static final Long CLIENT_ID = -1L;
	private static final Long SPECIALIST_ID = -1L;
	private static final Long TREATMENT_ID = -1L;
	private static final String TREATMENT_NAME = "Konsultacja dentystyczna";
	private static final String TREATMENT_DESCRIPTION = "Wstępna konsultacja stomatologiczna";
	private static final int TREATMENT_DURATION_MINUTES = 30;
	private static final Specialization SPECIALIST_SPECIALIZATION = Specialization.DENTIST;
	private static final AppointmentStatus APPOINTMENT_STATUS = AppointmentStatus.SCHEDULED;

	@Mock
	private AppointmentRepository appointmentRepository;

	@InjectMocks
	private FindAppointmentUcImpl findAppointmentUc;

	@Spy
	private AppointmentMapper appointmentMapper = Mappers.getMapper(AppointmentMapper.class);

	@Spy
	private TreatmentMapper treatmentMapper = Mappers.getMapper(TreatmentMapper.class);

	private Instant appointmentStart;
	private Instant appointmentEnd;

	@BeforeEach
	void setUp() {
		ReflectionTestUtils.setField(appointmentMapper, "treatmentMapper", treatmentMapper);
		appointmentStart = toInstant("2026-05-10 10:00:00");
		appointmentEnd = toInstant("2026-05-10 10:30:00");
	}

	@Test
	void shouldFindAppointmentById() {
		// given
		AppointmentEntity entity = createFullAppointmentEntity();
		when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(entity));

		// when
		Optional<AppointmentCto> result = findAppointmentUc.findById(APPOINTMENT_ID);

		// then
		assertThat(result).isPresent().hasValueSatisfying(cto -> {
			assertThat(cto.getAppointmentEto())
					.extracting(AppointmentEto::getId, AppointmentEto::getDateTime, AppointmentEto::getEndDateTime,
							AppointmentEto::getStatus)
					.containsExactly(APPOINTMENT_ID, appointmentStart, appointmentEnd, APPOINTMENT_STATUS);
			assertThat(cto.getClientEto().getId()).isEqualTo(CLIENT_ID);
			assertTreatmentCto(cto.getTreatmentCto());
		});
	}

	@Test
	void shouldReturnEmptyOptionalWhenAppointmentNotFound() {
		// given
		Long nonExistentId = -999L;
		when(appointmentRepository.findById(nonExistentId)).thenReturn(Optional.empty());

		// when
		Optional<AppointmentCto> result = findAppointmentUc.findById(nonExistentId);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void shouldFindAppointmentsByCriteria() {
		// given
		AppointmentCriteria criteria = new AppointmentCriteria(null, null, null, null);
		AppointmentEntity entity = createFullAppointmentEntity();
		when(appointmentRepository.findByCriteria(criteria)).thenReturn(List.of(entity));

		// when
		List<AppointmentCto> result = findAppointmentUc.findByCriteria(criteria);

		// then
		assertThat(result).hasSize(1).first().satisfies(cto -> {
			assertThat(cto.getAppointmentEto())
					.extracting(AppointmentEto::getId, AppointmentEto::getDateTime, AppointmentEto::getEndDateTime,
							AppointmentEto::getStatus)
					.containsExactly(APPOINTMENT_ID, appointmentStart, appointmentEnd, APPOINTMENT_STATUS);
			assertThat(cto.getClientEto().getId()).isEqualTo(CLIENT_ID);
			assertThat(cto.getTreatmentCto().getTreatmentEto().getName()).isEqualTo(TREATMENT_NAME);
			assertThat(cto.getTreatmentCto().getSpecialistEto().getSpecialization())
					.isEqualTo(SPECIALIST_SPECIALIZATION);
		});
	}

	@Test
	void shouldReturnEmptyListWhenNoCriteriaMatch() {
		// given
		AppointmentCriteria criteria = new AppointmentCriteria(null, null, null, null);
		when(appointmentRepository.findByCriteria(criteria)).thenReturn(List.of());

		// when
		List<AppointmentCto> result = findAppointmentUc.findByCriteria(criteria);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void shouldDetectConflictWhenSpecialistAlreadyHasAppointment() {
		// given
		when(appointmentRepository.hasConflictingAppointments(SPECIALIST_ID, appointmentStart, appointmentEnd))
				.thenReturn(true);

		// when
		boolean hasConflict = findAppointmentUc.hasConflictingAppointment(SPECIALIST_ID, appointmentStart, appointmentEnd);

		// then
		assertThat(hasConflict).isTrue();
	}

	@Test
	void shouldNotDetectConflictWhenSpecialistIsFree() {
		// given
		when(appointmentRepository.hasConflictingAppointments(SPECIALIST_ID, appointmentStart, appointmentEnd))
				.thenReturn(false);

		// when
		boolean hasConflict = findAppointmentUc.hasConflictingAppointment(SPECIALIST_ID, appointmentStart, appointmentEnd);

		// then
		assertThat(hasConflict).isFalse();
	}

	private void assertTreatmentCto(TreatmentCto treatmentCto) {
		assertThat(treatmentCto.getTreatmentEto())
				.extracting(TreatmentEto::getId, TreatmentEto::getName, TreatmentEto::getDescription,
						TreatmentEto::getDurationInMinutes)
				.containsExactly(TREATMENT_ID, TREATMENT_NAME, TREATMENT_DESCRIPTION, TREATMENT_DURATION_MINUTES);
		assertThat(treatmentCto.getSpecialistEto()).extracting(SpecialistEto::getId, SpecialistEto::getSpecialization)
				.containsExactly(SPECIALIST_ID, SPECIALIST_SPECIALIZATION);
	}

	private AppointmentEntity createFullAppointmentEntity() {
		SpecialistEntity specialist = new SpecialistEntity();
		specialist.setId(SPECIALIST_ID);
		specialist.setSpecialization(SPECIALIST_SPECIALIZATION);

		TreatmentEntity treatment = new TreatmentEntity();
		treatment.setId(TREATMENT_ID);
		treatment.setName(TREATMENT_NAME);
		treatment.setDescription(TREATMENT_DESCRIPTION);
		treatment.setDurationMinutes(TREATMENT_DURATION_MINUTES);
		treatment.setSpecialist(specialist);

		ClientEntity client = new ClientEntity();
		client.setId(CLIENT_ID);

		AppointmentEntity entity = new AppointmentEntity();
		entity.setId(APPOINTMENT_ID);
		entity.setDateTime(appointmentStart);
		entity.setEndDateTime(appointmentEnd);
		entity.setStatus(APPOINTMENT_STATUS);
		entity.setClient(client);
		entity.setTreatment(treatment);
		return entity;
	}
}

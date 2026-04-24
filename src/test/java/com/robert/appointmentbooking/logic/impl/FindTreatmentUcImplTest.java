package com.robert.appointmentbooking.logic.impl;

import com.robert.appointmentbooking.common.BaseTest;
import com.robert.appointmentbooking.common.datatype.Specialization;
import com.robert.appointmentbooking.common.to.SpecialistEto;
import com.robert.appointmentbooking.common.to.TreatmentCto;
import com.robert.appointmentbooking.common.to.TreatmentEto;
import com.robert.appointmentbooking.dataaccess.criteria.TreatmentCriteria;
import com.robert.appointmentbooking.dataaccess.entity.SpecialistEntity;
import com.robert.appointmentbooking.dataaccess.entity.TreatmentEntity;
import com.robert.appointmentbooking.dataaccess.repository.TreatmentRepository;
import com.robert.appointmentbooking.logic.mapper.TreatmentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FindTreatmentUcImplTest extends BaseTest {

	private static final Long TREATMENT_ID = -1L;
	private static final String TREATMENT_NAME = "USG serca";
	private static final String TREATMENT_DESCRIPTION = "Badanie profilaktyczne";
	private static final int TREATMENT_DURATION_MINUTES = 30;
	private static final Long SPECIALIST_ID = -1L;
	private static final Specialization SPECIALIST_SPECIALIZATION = Specialization.CARDIOLOGIST;

	@Mock
	private TreatmentRepository treatmentRepository;

	@InjectMocks
	private FindTreatmentUcImpl findTreatmentUc;

	@Spy
	private TreatmentMapper treatmentMapper = Mappers.getMapper(TreatmentMapper.class);

	@Test
	void shouldFindTreatmentById() {
		// given
		TreatmentEntity treatmentEntity = createFullTreatmentEntity();
		when(treatmentRepository.findById(TREATMENT_ID)).thenReturn(Optional.of(treatmentEntity));

		// when
		Optional<TreatmentCto> result = findTreatmentUc.findById(TREATMENT_ID);

		// then
		assertThat(result).isPresent().hasValueSatisfying(this::assertTreatmentCto);
	}

	@Test
	void shouldReturnEmptyOptionalWhenTreatmentNotFound() {
		// given
		Long nonExistentId = -999L;
		when(treatmentRepository.findById(nonExistentId)).thenReturn(Optional.empty());

		// when
		Optional<TreatmentCto> result = findTreatmentUc.findById(nonExistentId);

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void shouldFindAllTreatments() {
		// given
		TreatmentEntity treatmentEntity = createFullTreatmentEntity();
		when(treatmentRepository.findAll()).thenReturn(List.of(treatmentEntity));

		// when
		List<TreatmentCto> result = findTreatmentUc.findAll();

		// then
		assertThat(result).hasSize(1).first().satisfies(this::assertTreatmentCto);
	}

	@Test
	void shouldReturnEmptyListWhenNoTreatmentsExist() {
		// given
		when(treatmentRepository.findAll()).thenReturn(List.of());

		// when
		List<TreatmentCto> result = findTreatmentUc.findAll();

		// then
		assertThat(result).isEmpty();
	}

	@Test
	void shouldFindTreatmentByCriteria() {
		// given
		TreatmentCriteria criteria = new TreatmentCriteria(TREATMENT_NAME, TREATMENT_DESCRIPTION, 0, 30, -13L);
		TreatmentEntity treatmentEntity = createFullTreatmentEntity();
		when(treatmentRepository.findByCriteria(criteria)).thenReturn(List.of(treatmentEntity));

		// when
		List<TreatmentCto> result = findTreatmentUc.findByCriteria(criteria);

		// then
		assertThat(result).hasSize(1).first().satisfies(cto -> {
			assertThat(cto.getTreatmentEto())
					.extracting(TreatmentEto::getId, TreatmentEto::getName, TreatmentEto::getDescription,
							TreatmentEto::getDurationInMinutes)
					.containsExactly(TREATMENT_ID, TREATMENT_NAME, TREATMENT_DESCRIPTION, TREATMENT_DURATION_MINUTES);
			assertThat(cto.getSpecialistEto().getSpecialization()).isEqualTo(SPECIALIST_SPECIALIZATION);
		});
	}

	@Test
	void shouldReturnEmptyListWhenNoCriteriaMatch() {
		// given
		TreatmentCriteria criteria = new TreatmentCriteria("Non-existent", null, 0, 0, null);
		when(treatmentRepository.findByCriteria(criteria)).thenReturn(List.of());

		// when
		List<TreatmentCto> result = findTreatmentUc.findByCriteria(criteria);

		// then
		assertThat(result).isEmpty();
	}

	private void assertTreatmentCto(TreatmentCto cto) {
		assertThat(cto.getTreatmentEto())
				.extracting(TreatmentEto::getId, TreatmentEto::getName, TreatmentEto::getDescription,
						TreatmentEto::getDurationInMinutes)
				.containsExactly(TREATMENT_ID, TREATMENT_NAME, TREATMENT_DESCRIPTION, TREATMENT_DURATION_MINUTES);
		assertThat(cto.getSpecialistEto()).extracting(SpecialistEto::getId, SpecialistEto::getSpecialization)
				.containsExactly(SPECIALIST_ID, SPECIALIST_SPECIALIZATION);
	}

	private TreatmentEntity createFullTreatmentEntity() {
		SpecialistEntity specialist = new SpecialistEntity();
		specialist.setId(SPECIALIST_ID);
		specialist.setSpecialization(SPECIALIST_SPECIALIZATION);

		TreatmentEntity entity = new TreatmentEntity();
		entity.setId(TREATMENT_ID);
		entity.setName(TREATMENT_NAME);
		entity.setDescription(TREATMENT_DESCRIPTION);
		entity.setDurationMinutes(TREATMENT_DURATION_MINUTES);
		entity.setSpecialist(specialist);
		return entity;
	}
}

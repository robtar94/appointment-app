package com.robert.appointmentbooking.dataaccess.repository;

import java.util.List;

import com.robert.appointmentbooking.common.BaseDataJpaTest;
import com.robert.appointmentbooking.common.datatype.Specialization;
import com.robert.appointmentbooking.dataaccess.entity.TreatmentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TreatmentRepositoryIT extends BaseDataJpaTest {
	public static final String FIRST_TREATMENT = "Konsultacja";
	final static String SECOND_TREATMENT = "Leczenie kanałowe";

	@Autowired
	private TreatmentRepository treatmentRepository;

	@Test
	void shouldFindByTreatmentNameIgnoreCase() {
		// given && when
		List<TreatmentEntity> treatments = treatmentRepository.findAllByNameContainingIgnoreCase(FIRST_TREATMENT);
		// then
		assertThat(treatments).hasSize(4);
	}

	@Test
	void shouldFindByTreatmentName() {
		// given && when
		TreatmentEntity treatment = treatmentRepository.findByName(SECOND_TREATMENT);

		// then
		assertThat(treatment).isNotNull();
		assertThat(treatment.getName()).isEqualTo(SECOND_TREATMENT);
	}

	@Test
	void shouldFindBySpecialisation() {
		// given
		Specialization specialization = Specialization.DENTIST;

		// when
		List<TreatmentEntity> treatments = treatmentRepository.findBySpecialization(specialization);

		// then
		assertThat(treatments).hasSize(2).allSatisfy(t -> {
			assertThat(t.getSpecialist().getSpecialization()).isEqualTo(specialization);
		});
	}
}

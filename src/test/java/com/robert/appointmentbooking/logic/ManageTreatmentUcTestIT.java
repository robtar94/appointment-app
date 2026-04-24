package com.robert.appointmentbooking.logic;

import com.robert.appointmentbooking.common.BaseTest;
import com.robert.appointmentbooking.common.to.TreatmentCto;
import com.robert.appointmentbooking.config.AccessControl;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@WithMockUser(username = "admin", roles = {AccessControl.ROLE_ADMIN, AccessControl.ROLE_RECEPTIONIST,
		AccessControl.ROLE_SPECIALIST})
class ManageTreatmentUcTestIT extends BaseTest {

	@Inject
	private ManageTreatmentUc manageTreatmentUc;

	@Test
	void shouldCreateTreatment() {
		// given
		TreatmentCreationTo treatmentCreationTo = TreatmentCreationTo.builder().name("New Treatment")
				.description("New Treatment Description").durationMinutes(60).specialistId(-1L).build();

		// when
		TreatmentCto result = manageTreatmentUc.createTreatment(treatmentCreationTo);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getTreatmentEto().getId()).isNotNull();
		assertThat(result.getTreatmentEto().getName()).isEqualTo(treatmentCreationTo.name());
		assertThat(result.getTreatmentEto().getDescription()).isEqualTo(treatmentCreationTo.description());
		assertThat(result.getSpecialistEto().getId()).isEqualTo(treatmentCreationTo.specialistId());
	}

}

package com.capgemini.training.appointmentbooking.logic;

import com.capgemini.training.appointmentbooking.common.BaseTest;
import com.capgemini.training.appointmentbooking.common.datatype.AppointmentStatus;
import com.capgemini.training.appointmentbooking.common.to.AppointmentCto;
import com.capgemini.training.appointmentbooking.common.to.AppointmentEto;
import com.capgemini.training.appointmentbooking.config.AccessControl;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.Instant;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@WithMockUser(username = "admin", roles = {AccessControl.ROLE_ADMIN, AccessControl.ROLE_RECEPTIONIST,
		AccessControl.ROLE_SPECIALIST})
class ManageAppointmentUcTestIT extends BaseTest {

	@Inject
	private ManageAppointmentUc manageAppointmentUc;

	@Test
	void shouldBookAppointment() {
		// given
		AppointmentBookingEto bookingEto = AppointmentBookingEto.builder().clientId(-1L).treatmentId(-1L)
				.dateTime(Instant.now()).endDateTime(Instant.now().plusSeconds(3600)).build();

		// when
		AppointmentCto result = manageAppointmentUc.bookAppointment(bookingEto);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getAppointmentEto().getId()).isNotNull();
		assertThat(result.getAppointmentEto().getStatus()).isEqualTo(AppointmentStatus.SCHEDULED);
		assertThat(result.getClientEto().getId()).isEqualTo(bookingEto.clientId());
		assertThat(result.getTreatmentCto().getTreatmentEto().getId()).isEqualTo(bookingEto.treatmentId());
	}

	@Test
	void shouldUpdateAppointmentStatus() {
		// given
		Long appointmentId = -1L;
		AppointmentStatus newStatus = AppointmentStatus.COMPLETED;

		// when
		AppointmentEto result = manageAppointmentUc.updateAppointmentStatus(appointmentId, newStatus);

		// then
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(appointmentId);
		assertThat(result.getStatus()).isEqualTo(newStatus);
	}

}

package com.capgemini.training.appointmentbooking.service.impl;

import com.capgemini.training.appointmentbooking.common.BaseTest;
import com.capgemini.training.appointmentbooking.common.datatype.AppointmentStatus;
import com.capgemini.training.appointmentbooking.common.datatype.Specialization;
import com.capgemini.training.appointmentbooking.common.to.AppointmentCto;
import com.capgemini.training.appointmentbooking.common.to.AppointmentEto;
import com.capgemini.training.appointmentbooking.common.to.ClientEto;
import com.capgemini.training.appointmentbooking.common.to.SpecialistEto;
import com.capgemini.training.appointmentbooking.common.to.TreatmentCto;
import com.capgemini.training.appointmentbooking.common.to.TreatmentEto;
import com.capgemini.training.appointmentbooking.dataaccess.criteria.AppointmentCriteria;
import com.capgemini.training.appointmentbooking.logic.FindAppointmentUc;
import com.capgemini.training.appointmentbooking.logic.ManageAppointmentUc;
import com.capgemini.training.appointmentbooking.service.config.ServiceMappingConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AppointmentsApiController.class)
@Import(ServiceMappingConfiguration.class)
class AppointmentsApiControllerTest extends BaseTest {

	private static final Long APPOINTMENT_ID = -1L;
	private static final Long CLIENT_ID = -1L;
	private static final Long SPECIALIST_ID = -1L;
	private static final Long TREATMENT_ID = -1L;
	private static final String TREATMENT_NAME = "Consultation";
	private static final int TREATMENT_DURATION = 30;
	private static final String APPOINTMENT_DATE = "2026-05-10T10:00:00Z";

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private FindAppointmentUc findAppointmentUc;

	@MockitoBean
	private ManageAppointmentUc manageAppointmentUc;

	private Instant appointmentStart;
	private AppointmentCto appointmentCto;

	@BeforeEach
	void setUp() {
		appointmentStart = toInstant("2026-05-10 10:00:00");
		appointmentCto = createAppointmentCto();
	}

	@Test
	void shouldCreateAppointmentAndReturn201() throws Exception {
		// given
		String requestBody = "{\"clientId\": " + CLIENT_ID + ", \"treatmentId\": " + TREATMENT_ID
				+ ", \"dateTime\": \"2026-05-10T10:00:00.000Z\"}";
		when(manageAppointmentUc.bookAppointment(any())).thenReturn(appointmentCto);

		// when & then
		mockMvc.perform(post("/api/v1/appointments").contentType(MediaType.APPLICATION_JSON).with(csrf())
				.with(user("admin").roles("ADMIN")).content(requestBody)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(APPOINTMENT_ID.intValue()))
				.andExpect(jsonPath("$.clientId").value(CLIENT_ID.intValue()))
				.andExpect(jsonPath("$.treatmentId").value(TREATMENT_ID.intValue()));
	}

	@Test
	void shouldGetAppointmentsAndReturn200() throws Exception {
		// given
		when(findAppointmentUc.findByCriteria(any(AppointmentCriteria.class)))
				.thenReturn(List.of(appointmentCto));

		// when & then
		mockMvc.perform(get("/api/v1/appointments").with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(APPOINTMENT_ID.intValue()))
				.andExpect(jsonPath("$[0].clientId").value(CLIENT_ID.intValue()));
	}

	@Test
	void shouldGetAppointmentsWithClientIdFilterAndReturn200() throws Exception {
		// given
		when(findAppointmentUc.findByCriteria(any(AppointmentCriteria.class)))
				.thenReturn(List.of(appointmentCto));

		// when & then
		mockMvc.perform(get("/api/v1/appointments")
				.param("clientId", CLIENT_ID.toString())
				.with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(APPOINTMENT_ID.intValue()))
				.andExpect(jsonPath("$[0].status").value("SCHEDULED"));
	}

	@Test
	void shouldUpdateAppointmentStatusAndReturn204() throws Exception {
		// given
		String statusUpdateBody = "{\"status\": \"COMPLETED\"}";

		// when & then
		mockMvc.perform(
				patch("/api/v1/appointments/{appointmentId}", APPOINTMENT_ID).contentType(MediaType.APPLICATION_JSON)
						.with(csrf()).with(user("admin").roles("ADMIN")).content(statusUpdateBody))
				.andExpect(status().isNoContent());
	}

	@Test
	void shouldCheckAvailabilityWhenSpecialistIsFreeAndReturnTrue() throws Exception {
		// given
		when(findAppointmentUc.hasConflictingAppointment(anyLong(), any(Instant.class), any(Instant.class)))
				.thenReturn(false);

		// when & then
		mockMvc.perform(get("/api/v1/availability")
				.param("specialistId", SPECIALIST_ID.toString())
				.param("dateTime", APPOINTMENT_DATE)
				.with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.available").value(true));
	}

	@Test
	void shouldCheckAvailabilityWhenSpecialistIsBusyAndReturnFalse() throws Exception {
		// given
		when(findAppointmentUc.hasConflictingAppointment(anyLong(), any(Instant.class), any(Instant.class)))
				.thenReturn(true);

		// when & then
		mockMvc.perform(get("/api/v1/availability")
				.param("specialistId", SPECIALIST_ID.toString())
				.param("dateTime", APPOINTMENT_DATE)
				.with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.available").value(false));
	}

	private AppointmentCto createAppointmentCto() {
		final Instant appointmentEnd = appointmentStart.plusSeconds(TREATMENT_DURATION * 60L);

		final AppointmentEto appointmentEto = AppointmentEto.builder().id(APPOINTMENT_ID).dateTime(appointmentStart)
				.endDateTime(appointmentEnd).status(AppointmentStatus.SCHEDULED).build();

		final ClientEto clientEto = ClientEto.builder().id(CLIENT_ID).build();

		final SpecialistEto specialistEto = SpecialistEto.builder().id(SPECIALIST_ID)
				.specialization(Specialization.CARDIOLOGIST).build();

		final TreatmentEto treatmentEto = TreatmentEto.builder().id(TREATMENT_ID).name(TREATMENT_NAME)
				.description("Treatment description").durationInMinutes(TREATMENT_DURATION).build();

		final TreatmentCto treatmentCto = TreatmentCto.builder().treatmentEto(treatmentEto).specialistEto(specialistEto)
				.build();

		return AppointmentCto.builder().appointmentEto(appointmentEto).clientEto(clientEto).treatmentCto(treatmentCto)
				.build();
	}
}
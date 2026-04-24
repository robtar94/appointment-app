package com.robert.appointmentbooking.service.impl;

import com.robert.appointmentbooking.common.BaseTest;
import com.robert.appointmentbooking.common.datatype.Specialization;
import com.robert.appointmentbooking.common.to.SpecialistEto;
import com.robert.appointmentbooking.common.to.TreatmentCto;
import com.robert.appointmentbooking.common.to.TreatmentEto;
import com.robert.appointmentbooking.logic.FindTreatmentUc;
import com.robert.appointmentbooking.logic.ManageTreatmentUc;
import com.robert.appointmentbooking.logic.TreatmentCreationTo;
import com.robert.appointmentbooking.service.config.ServiceMappingConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TreatmentsApiController.class)
@Import(ServiceMappingConfiguration.class)
class TreatmentsApiControllerTest extends BaseTest {

	private static final Long TREATMENT_ID = -1L;
	private static final Long SPECIALIST_ID = -1L;
	private static final String TREATMENT_NAME = "USG serca";
	private static final String TREATMENT_DESCRIPTION = "Badanie kardiologiczne";
	private static final int TREATMENT_DURATION = 30;
	private static final Specialization SPECIALIST_SPECIALIZATION = Specialization.CARDIOLOGIST;

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private FindTreatmentUc findTreatmentUc;

	@MockitoBean
	private ManageTreatmentUc manageTreatmentUc;

	private TreatmentCto treatmentCto;

	@BeforeEach
	void setUp() {
		treatmentCto = createTreatmentCto();
	}

	@Test
	void shouldCreateTreatmentAndReturn201() throws Exception {
		// given
		final String requestBody = "{\"name\": \"" + TREATMENT_NAME + "\", \"duration\": " + TREATMENT_DURATION
				+ ", \"specialistId\": " + SPECIALIST_ID + "}";

		when(manageTreatmentUc.createTreatment(any(TreatmentCreationTo.class))).thenReturn(treatmentCto);

		// when & then
		mockMvc.perform(post("/api/v1/treatments").contentType(MediaType.APPLICATION_JSON).with(csrf())
				.with(user("admin").roles("ADMIN")).content(requestBody)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(TREATMENT_ID.intValue()))
				.andExpect(jsonPath("$.name").value(TREATMENT_NAME))
				.andExpect(jsonPath("$.duration").value(TREATMENT_DURATION))
				.andExpect(jsonPath("$.specialistId").value(SPECIALIST_ID.intValue()));
	}

	@Test
	void shouldGetTreatmentDetailsAndReturn200() throws Exception {
		// given
		when(findTreatmentUc.findById(TREATMENT_ID))
				.thenReturn(Optional.of(treatmentCto));

		// when & then
		mockMvc.perform(get("/api/v1/treatments/{treatmentId}", TREATMENT_ID).with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(TREATMENT_ID.intValue()))
				.andExpect(jsonPath("$.name").value(TREATMENT_NAME))
				.andExpect(jsonPath("$.duration").value(TREATMENT_DURATION))
				.andExpect(jsonPath("$.specialist.name").value(SPECIALIST_SPECIALIZATION.name()));
	}

	@Test
	void shouldGetTreatmentDetailsAndReturn404WhenNotFound() throws Exception {
		// given
		when(findTreatmentUc.findById(TREATMENT_ID))
				.thenReturn(Optional.empty());

		// when & then
		mockMvc.perform(get("/api/v1/treatments/{treatmentId}", TREATMENT_ID).with(user("admin").roles("ADMIN")))
				.andExpect(status().isNotFound());
	}

	@Test
	void shouldGetTreatmentsAndReturn200() throws Exception {
		// given
		when(findTreatmentUc.findAll())
				.thenReturn(List.of(treatmentCto));

		// when & then
		mockMvc.perform(get("/api/v1/treatments").with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(TREATMENT_ID.intValue()))
				.andExpect(jsonPath("$[0].name").value(TREATMENT_NAME))
				.andExpect(jsonPath("$[0].duration").value(TREATMENT_DURATION));
	}

	@Test
	void shouldGetTreatmentsAndReturnEmptyList() throws Exception {
		// given
		when(findTreatmentUc.findAll())
				.thenReturn(List.of());

		// when & then
		mockMvc.perform(get("/api/v1/treatments").with(user("admin").roles("ADMIN")))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$.length()").value(0));
	}

	private TreatmentCto createTreatmentCto() {
		final TreatmentEto treatmentEto = TreatmentEto.builder().id(TREATMENT_ID).name(TREATMENT_NAME)
				.description(TREATMENT_DESCRIPTION).durationInMinutes(TREATMENT_DURATION).build();

		final SpecialistEto specialistEto = SpecialistEto.builder().id(SPECIALIST_ID)
				.specialization(SPECIALIST_SPECIALIZATION).build();

		return TreatmentCto.builder().treatmentEto(treatmentEto).specialistEto(specialistEto).build();
	}
}
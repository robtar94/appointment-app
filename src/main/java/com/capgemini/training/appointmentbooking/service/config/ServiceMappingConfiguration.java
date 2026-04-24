package com.capgemini.training.appointmentbooking.service.config;

import com.capgemini.training.appointmentbooking.service.mapper.AppointmentApiMapper;
import com.capgemini.training.appointmentbooking.service.mapper.TreatmentApiMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceMappingConfiguration {

	@Bean
	AppointmentApiMapper getAppointmentApiMapper() {
		return new AppointmentApiMapper();
	}

	@Bean
	TreatmentApiMapper getTreatmentApiMapper() {
		return new TreatmentApiMapper();
	}
}

package com.robert.appointmentbooking.service.config;

import com.robert.appointmentbooking.service.mapper.AppointmentApiMapper;
import com.robert.appointmentbooking.service.mapper.TreatmentApiMapper;
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

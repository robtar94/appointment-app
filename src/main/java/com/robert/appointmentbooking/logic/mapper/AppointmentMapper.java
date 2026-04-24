package com.robert.appointmentbooking.logic.mapper;

import com.robert.appointmentbooking.common.to.AppointmentCto;
import com.robert.appointmentbooking.common.to.AppointmentEto;
import com.robert.appointmentbooking.dataaccess.entity.AppointmentEntity;
import com.robert.appointmentbooking.logic.AppointmentBookingEto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TreatmentMapper.class})
public interface AppointmentMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "version", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "client.id", source = "clientId")
	@Mapping(target = "treatment.id", source = "treatmentId")
	AppointmentEntity toAppointmentEntity(AppointmentBookingEto appointmentBookingEto);
	@Mapping(target = "appointmentEto", source = "entity")
	@Mapping(target = "clientEto", source = "entity.client")
	@Mapping(target = "treatmentCto", source = "entity.treatment")

	AppointmentCto toAppointmentCto(AppointmentEntity entity);

	AppointmentEto toAppointmentEto(AppointmentEntity entity);

}

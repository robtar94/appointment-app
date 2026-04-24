package com.capgemini.training.appointmentbooking.logic;

import com.capgemini.training.appointmentbooking.common.to.AppointmentCto;
import com.capgemini.training.appointmentbooking.config.roles.IsReceptionist;
import com.capgemini.training.appointmentbooking.dataaccess.criteria.AppointmentCriteria;

import java.util.List;
import java.util.Optional;

public interface FindAppointmentUc {

	@IsReceptionist
	Optional<AppointmentCto> findById(Long id);

	@IsReceptionist
	List<AppointmentCto> findAll();

	@IsReceptionist
	List<AppointmentCto> findByCriteria(AppointmentCriteria criteria);

	@IsReceptionist
	boolean hasConflictingAppointment(Long specialistId, java.time.Instant dateTime, java.time.Instant endDateTime);
}

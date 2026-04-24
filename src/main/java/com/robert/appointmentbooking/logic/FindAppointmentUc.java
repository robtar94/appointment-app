package com.robert.appointmentbooking.logic;

import com.robert.appointmentbooking.common.to.AppointmentCto;
import com.robert.appointmentbooking.config.roles.IsReceptionist;
import com.robert.appointmentbooking.dataaccess.criteria.AppointmentCriteria;

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

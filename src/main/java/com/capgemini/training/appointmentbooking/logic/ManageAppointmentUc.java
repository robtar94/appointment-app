package com.capgemini.training.appointmentbooking.logic;

import com.capgemini.training.appointmentbooking.common.datatype.AppointmentStatus;
import com.capgemini.training.appointmentbooking.common.to.AppointmentCto;
import com.capgemini.training.appointmentbooking.common.to.AppointmentEto;
import com.capgemini.training.appointmentbooking.config.roles.IsReceptionist;
import com.capgemini.training.appointmentbooking.config.roles.IsSpecialist;

public interface ManageAppointmentUc {

	@IsReceptionist
	AppointmentCto bookAppointment(AppointmentBookingEto appointmentBookingEto);

	@IsSpecialist
	AppointmentEto updateAppointmentStatus(Long appointmentId, AppointmentStatus appointmentStatus);
}

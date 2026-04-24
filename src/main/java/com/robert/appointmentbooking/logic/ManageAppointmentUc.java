package com.robert.appointmentbooking.logic;

import com.robert.appointmentbooking.common.datatype.AppointmentStatus;
import com.robert.appointmentbooking.common.to.AppointmentCto;
import com.robert.appointmentbooking.common.to.AppointmentEto;
import com.robert.appointmentbooking.config.roles.IsReceptionist;
import com.robert.appointmentbooking.config.roles.IsSpecialist;

public interface ManageAppointmentUc {

	@IsReceptionist
	AppointmentCto bookAppointment(AppointmentBookingEto appointmentBookingEto);

	@IsSpecialist
	AppointmentEto updateAppointmentStatus(Long appointmentId, AppointmentStatus appointmentStatus);
}

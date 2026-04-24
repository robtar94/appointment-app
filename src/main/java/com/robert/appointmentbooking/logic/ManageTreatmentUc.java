package com.robert.appointmentbooking.logic;

import com.robert.appointmentbooking.common.to.TreatmentCto;
import com.robert.appointmentbooking.config.roles.IsAdmin;

public interface ManageTreatmentUc {

	@IsAdmin
	TreatmentCto createTreatment(TreatmentCreationTo treatmentCreationTo);
}

package com.capgemini.training.appointmentbooking.logic;

import com.capgemini.training.appointmentbooking.common.to.TreatmentCto;
import com.capgemini.training.appointmentbooking.config.roles.IsAdmin;

public interface ManageTreatmentUc {

	@IsAdmin
	TreatmentCto createTreatment(TreatmentCreationTo treatmentCreationTo);
}

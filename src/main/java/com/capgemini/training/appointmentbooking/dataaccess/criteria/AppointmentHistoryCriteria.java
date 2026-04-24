package com.capgemini.training.appointmentbooking.dataaccess.criteria;

import com.capgemini.training.appointmentbooking.common.datatype.AppointmentStatus;

import java.time.Instant;

public record AppointmentHistoryCriteria(Long clientId, AppointmentStatus status, Instant fromDate) {
	public AppointmentHistoryCriteria {
		if (clientId == null) {
			throw new IllegalArgumentException("clientId is required");
		}
	}

	public AppointmentHistoryCriteria(Long clientId) {
		this(clientId, null, null);
	}
}

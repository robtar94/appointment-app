package com.robert.appointmentbooking.dataaccess.criteria;

import com.robert.appointmentbooking.common.datatype.AppointmentStatus;

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

package com.robert.appointmentbooking.dataaccess.criteria;

import java.time.LocalDate;

import com.robert.appointmentbooking.common.datatype.AppointmentStatus;

public record AppointmentCriteria(LocalDate startDate, LocalDate endDate, AppointmentStatus status, Long clientId) {

}

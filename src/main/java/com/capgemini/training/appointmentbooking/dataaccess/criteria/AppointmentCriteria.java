package com.capgemini.training.appointmentbooking.dataaccess.criteria;

import java.time.LocalDate;

import com.capgemini.training.appointmentbooking.common.datatype.AppointmentStatus;

public record AppointmentCriteria(LocalDate startDate, LocalDate endDate, AppointmentStatus status, Long clientId) {

}

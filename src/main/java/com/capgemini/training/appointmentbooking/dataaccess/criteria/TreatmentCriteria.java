package com.capgemini.training.appointmentbooking.dataaccess.criteria;

public record TreatmentCriteria(String name, String description, Integer durationMinutesFrom, Integer durationMinutesTo,
		Long specialistId) {
}

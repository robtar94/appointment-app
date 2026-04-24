package com.capgemini.training.appointmentbooking.dataaccess.converter;

import com.capgemini.training.appointmentbooking.common.datatype.Specialization;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SpecializationConverter implements AttributeConverter<Specialization, String> {

	@Override
	public String convertToDatabaseColumn(Specialization specialization) {
		return specialization != null ? specialization.getName() : null;
	}

	@Override
	public Specialization convertToEntityAttribute(String dbData) {
		return dbData != null ? Specialization.getByName(dbData) : null;
	}
}

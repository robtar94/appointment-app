package com.capgemini.training.appointmentbooking.logic.mapper;

import com.capgemini.training.appointmentbooking.common.to.TreatmentCto;
import com.capgemini.training.appointmentbooking.common.to.TreatmentEto;
import com.capgemini.training.appointmentbooking.dataaccess.entity.TreatmentEntity;
import com.capgemini.training.appointmentbooking.logic.TreatmentCreationTo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreatmentMapper {

	@Mapping(target = "treatmentEto", source = "entity")
	@Mapping(target = "specialistEto", source = "entity.specialist")
	TreatmentCto toTreatmentCto(TreatmentEntity entity);

	@Mapping(source = "durationMinutes", target = "durationInMinutes")
	TreatmentEto toTreatmentEto(TreatmentEntity entity);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "specialist", ignore = true)
	@Mapping(target = "version", ignore = true)
	TreatmentEntity toTreatmentEntity(TreatmentCreationTo treatmentCreationTo);

}

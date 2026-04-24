package com.robert.appointmentbooking.logic.impl;

import com.robert.appointmentbooking.common.to.TreatmentCto;
import com.robert.appointmentbooking.dataaccess.entity.SpecialistEntity;
import com.robert.appointmentbooking.dataaccess.entity.TreatmentEntity;
import com.robert.appointmentbooking.dataaccess.repository.SpecialistRepository;
import com.robert.appointmentbooking.dataaccess.repository.TreatmentRepository;
import com.robert.appointmentbooking.logic.ManageTreatmentUc;
import com.robert.appointmentbooking.logic.TreatmentCreationTo;
import com.robert.appointmentbooking.logic.mapper.TreatmentMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class ManageTreatmentUcImpl implements ManageTreatmentUc {

	private final TreatmentRepository treatmentRepository;

	private final SpecialistRepository specialistRepository;

	private final TreatmentMapper treatmentMapper;

	@Override
	public TreatmentCto createTreatment(TreatmentCreationTo treatmentCreationTo) {
		TreatmentEntity treatmentEntity = toTreatmentEntity(treatmentCreationTo);
		SpecialistEntity specialist = this.specialistRepository.findById(treatmentCreationTo.specialistId())
				.orElseThrow(() -> new IllegalArgumentException(
						"Specialist with id " + treatmentCreationTo.specialistId() + " not found"));
		treatmentEntity.setSpecialist(specialist);
		treatmentEntity = treatmentRepository.saveAndFlush(treatmentEntity);
		return toTreatmentCto(treatmentEntity);
	}

	private TreatmentEntity toTreatmentEntity(TreatmentCreationTo treatmentCreationTo) {
		return this.treatmentMapper.toTreatmentEntity(treatmentCreationTo);
	}

	private TreatmentCto toTreatmentCto(TreatmentEntity treatmentEntity) {
		return this.treatmentMapper.toTreatmentCto(treatmentEntity);
	}

}
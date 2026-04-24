package com.capgemini.training.appointmentbooking.logic.impl;

import com.capgemini.training.appointmentbooking.common.to.TreatmentCto;
import com.capgemini.training.appointmentbooking.dataaccess.criteria.TreatmentCriteria;
import com.capgemini.training.appointmentbooking.dataaccess.repository.TreatmentRepository;
import com.capgemini.training.appointmentbooking.logic.FindTreatmentUc;
import com.capgemini.training.appointmentbooking.logic.mapper.TreatmentMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class FindTreatmentUcImpl implements FindTreatmentUc {

	private final TreatmentRepository treatmentRepository;

	private final TreatmentMapper treatmentMapper;

	@Override
	public Optional<TreatmentCto> findById(Long id) {
		return this.treatmentRepository.findById(id).map(this.treatmentMapper::toTreatmentCto);
	}

	@Override
	public List<TreatmentCto> findAll() {
		return this.treatmentRepository.findAll().stream().map(this.treatmentMapper::toTreatmentCto).toList();
	}

	@Override
	public List<TreatmentCto> findByCriteria(TreatmentCriteria criteria) {
		return this.treatmentRepository.findByCriteria(criteria).stream().map(this.treatmentMapper::toTreatmentCto)
				.toList();
	}
}

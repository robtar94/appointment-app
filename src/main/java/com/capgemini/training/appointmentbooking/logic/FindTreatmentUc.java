package com.capgemini.training.appointmentbooking.logic;

import com.capgemini.training.appointmentbooking.common.to.TreatmentCto;
import com.capgemini.training.appointmentbooking.dataaccess.criteria.TreatmentCriteria;

import java.util.List;
import java.util.Optional;

public interface FindTreatmentUc {

	Optional<TreatmentCto> findById(Long id);

	List<TreatmentCto> findAll();

	List<TreatmentCto> findByCriteria(TreatmentCriteria criteria);
}

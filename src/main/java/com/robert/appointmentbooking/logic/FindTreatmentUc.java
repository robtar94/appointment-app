package com.robert.appointmentbooking.logic;

import com.robert.appointmentbooking.common.to.TreatmentCto;
import com.robert.appointmentbooking.dataaccess.criteria.TreatmentCriteria;

import java.util.List;
import java.util.Optional;

public interface FindTreatmentUc {

	Optional<TreatmentCto> findById(Long id);

	List<TreatmentCto> findAll();

	List<TreatmentCto> findByCriteria(TreatmentCriteria criteria);
}

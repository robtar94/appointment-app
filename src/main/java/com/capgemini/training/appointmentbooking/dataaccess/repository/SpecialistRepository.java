package com.capgemini.training.appointmentbooking.dataaccess.repository;

import java.util.List;

import com.capgemini.training.appointmentbooking.common.datatype.Specialization;
import com.capgemini.training.appointmentbooking.dataaccess.entity.SpecialistEntity;

public interface SpecialistRepository extends BaseJpaRepository<SpecialistEntity, Long> {

	SpecialistEntity findByUserId(final Long userId);

	List<SpecialistEntity> findBySpecialization(Specialization specialization);

}

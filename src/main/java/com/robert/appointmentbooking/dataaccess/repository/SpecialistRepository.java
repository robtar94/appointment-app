package com.robert.appointmentbooking.dataaccess.repository;

import java.util.List;

import com.robert.appointmentbooking.common.datatype.Specialization;
import com.robert.appointmentbooking.dataaccess.entity.SpecialistEntity;

public interface SpecialistRepository extends BaseJpaRepository<SpecialistEntity, Long> {

	SpecialistEntity findByUserId(final Long userId);

	List<SpecialistEntity> findBySpecialization(Specialization specialization);

}

package com.capgemini.training.appointmentbooking.dataaccess.repository;

import java.util.List;

import com.capgemini.training.appointmentbooking.common.datatype.Specialization;
import com.capgemini.training.appointmentbooking.dataaccess.criteria.TreatmentCriteria;
import com.capgemini.training.appointmentbooking.dataaccess.entity.QSpecialistEntity;
import com.capgemini.training.appointmentbooking.dataaccess.entity.QTreatmentEntity;
import com.capgemini.training.appointmentbooking.dataaccess.entity.TreatmentEntity;
import com.querydsl.core.BooleanBuilder;

public interface TreatmentRepository extends BaseJpaRepository<TreatmentEntity, Long> {
	List<TreatmentEntity> findAllByNameContainingIgnoreCase(String name);

	TreatmentEntity findByName(String name);

	default List<TreatmentEntity> findBySpecialization(Specialization specialization) {
		QTreatmentEntity treatment = QTreatmentEntity.treatmentEntity;
		QSpecialistEntity specialist = QSpecialistEntity.specialistEntity;

		return getQueryFactory().selectFrom(treatment).join(treatment.specialist, specialist)
				.where(specialist.specialization.eq(specialization)).fetch();
	}

	default List<TreatmentEntity> findByCriteria(TreatmentCriteria criteria) {
		QTreatmentEntity treatment = QTreatmentEntity.treatmentEntity;

		BooleanBuilder where = new BooleanBuilder();
		if (criteria.name() != null && !criteria.name().isBlank()) {
			where.and(treatment.name.likeIgnoreCase("%" + criteria.name() + "%"));
		}

		if (criteria.description() != null && !criteria.description().isBlank()) {
			where.and(treatment.description.likeIgnoreCase("%" + criteria.description() + "%"));
		}

		if (criteria.durationMinutesFrom() != null) {
			where.and(treatment.durationMinutes.goe(criteria.durationMinutesFrom()));
		}

		if (criteria.durationMinutesTo() != null) {
			where.and(treatment.durationMinutes.loe(criteria.durationMinutesTo()));
		}

		if (criteria.specialistId() != null) {
			where.and(treatment.specialist.id.eq(criteria.specialistId()));
		}

		return getQueryFactory().selectFrom(treatment).where(where).fetch();
	}

}

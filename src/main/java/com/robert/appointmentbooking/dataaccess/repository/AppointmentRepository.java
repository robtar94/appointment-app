package com.robert.appointmentbooking.dataaccess.repository;

import com.robert.appointmentbooking.dataaccess.criteria.AppointmentCriteria;
import com.robert.appointmentbooking.dataaccess.criteria.AppointmentHistoryCriteria;
import com.robert.appointmentbooking.dataaccess.entity.AppointmentEntity;
import com.robert.appointmentbooking.dataaccess.entity.QAppointmentEntity;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

public interface AppointmentRepository extends BaseJpaRepository<AppointmentEntity, Long> {

	@Query("""
			SELECT a FROM AppointmentEntity a
			JOIN a.treatment t
			WHERE t.specialist.id = :specialistId
			AND a.dateTime < :date
			AND a.status != com.robert.appointmentbooking.common.datatype.AppointmentStatus.CANCELLED
			ORDER BY a.dateTime DESC
			""")
	List<AppointmentEntity> findPastAppointmentsBySpecialist(@Param("specialistId") Long specialistId,
			@Param("date") Instant date);

	@Query("""
			SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
			FROM AppointmentEntity a
			JOIN a.treatment t
			WHERE t.specialist.id = :specialistId
			AND a.dateTime < :appointmentEndTime
			AND a.endDateTime > :appointmentStartTime
			AND a.status != com.robert.appointmentbooking.common.datatype.AppointmentStatus.CANCELLED
			""")
	boolean hasConflictingAppointments(@Param("specialistId") Long specialistId,
			@Param("appointmentStartTime") Instant appointmentStartTime,
			@Param("appointmentEndTime") Instant appointmentEndTime);

	@Query("""
			SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
			FROM AppointmentEntity a
			WHERE a.client.id = :clientId
			AND a.dateTime < :appointmentEndTime
			AND a.endDateTime > :appointmentStartTime
			AND a.status != com.robert.appointmentbooking.common.datatype.AppointmentStatus.CANCELLED
			""")
	boolean hasClientConflictingAppointments(@Param("clientId") Long clientId,
			@Param("appointmentStartTime") Instant appointmentStartTime,
			@Param("appointmentEndTime") Instant appointmentEndTime);

	default List<AppointmentEntity> findByCriteria(AppointmentCriteria criteria) {
		QAppointmentEntity appointment = QAppointmentEntity.appointmentEntity;

		BooleanBuilder where = new BooleanBuilder();
		if (criteria.clientId() != null) {
			where.and(appointment.client.id.eq(criteria.clientId()));
		}

		if (criteria.status() != null) {
			where.and(appointment.status.eq(criteria.status()));
		}

		if (criteria.startDate() != null) {
			where.and(appointment.dateTime.goe(criteria.startDate().atStartOfDay().toInstant(ZoneOffset.UTC)));
		}

		if (criteria.endDate() != null) {
			where.and(appointment.dateTime.loe(criteria.endDate().atTime(23, 59, 59).toInstant(ZoneOffset.UTC)));
		}

		return getQueryFactory().selectFrom(appointment).where(where).orderBy(appointment.dateTime.desc()).fetch();
	}

	default List<AppointmentEntity> findByHistoryCriteria(AppointmentHistoryCriteria criteria) {
		QAppointmentEntity appointment = QAppointmentEntity.appointmentEntity;

		BooleanBuilder where = new BooleanBuilder();
		where.and(appointment.client.id.eq(criteria.clientId()));

		if (criteria.status() != null) {
			where.and(appointment.status.eq(criteria.status()));
		}

		if (criteria.fromDate() != null) {
			where.and(appointment.dateTime.goe(criteria.fromDate()));
		}

		return getQueryFactory().selectFrom(appointment).where(where).orderBy(appointment.dateTime.desc()).fetch();
	}
}
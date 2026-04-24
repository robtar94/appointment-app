package com.capgemini.training.appointmentbooking.dataaccess.entity;

import com.capgemini.training.appointmentbooking.common.datatype.AppointmentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "APPOINTMENT")
@Getter
@Setter
public class AppointmentEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APPOINTMENT_SEQ_GEN")
	@SequenceGenerator(sequenceName = "APPOINTMENT_SEQ", name = "APPOINTMENT_SEQ_GEN", allocationSize = 100, initialValue = 1)
	private Long id;

	@Column(name = "DATE_TIME")
	private Instant dateTime;

	@Column(name = "END_DATE_TIME")
	private Instant endDateTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false)
	private AppointmentStatus status = AppointmentStatus.SCHEDULED;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLIENT_ID")
	private ClientEntity client;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "TREATMENT_ID", nullable = false)
	private TreatmentEntity treatment;

}
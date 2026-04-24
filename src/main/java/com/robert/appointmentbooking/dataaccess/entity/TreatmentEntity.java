package com.robert.appointmentbooking.dataaccess.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "TREATMENT")
@Getter
@Setter
@NamedQuery(name = "TreatmentEntity.findByName", query = "SELECT t FROM TreatmentEntity t WHERE t.name =:name")
public class TreatmentEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TREATMENT_SEQ_GEN")
	@SequenceGenerator(sequenceName = "TREATMENT_SEQ", name = "TREATMENT_SEQ_GEN", allocationSize = 100, initialValue = 1)
	private Long id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DURATION_MINUTES")
	private int durationMinutes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPECIALIST_ID")
	private SpecialistEntity specialist;
}
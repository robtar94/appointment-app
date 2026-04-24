package com.robert.appointmentbooking.dataaccess.entity;

import java.util.ArrayList;
import java.util.List;

import com.robert.appointmentbooking.common.datatype.Specialization;
import com.robert.appointmentbooking.dataaccess.converter.SpecializationConverter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "SPECIALIST")
@Getter
@Setter
@NamedQuery(name = "SpecialistEntity.findBySpecialization", query = "SELECT s FROM SpecialistEntity s WHERE s.specialization = :specialization")
public class SpecialistEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SPECIALIST_SEQ_GEN")
	@SequenceGenerator(sequenceName = "SPECIALIST_SEQ", name = "SPECIALIST_SEQ_GEN", allocationSize = 100, initialValue = 1)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "USER_ID", nullable = false)
	private UserEntity user;

	@Convert(converter = SpecializationConverter.class)
	@Column(name = "SPECIALIZATION", nullable = false)
	private Specialization specialization;

	@OneToMany(mappedBy = "specialist", cascade = {CascadeType.PERSIST,
			CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<TreatmentEntity> treatments = new ArrayList<>();
}

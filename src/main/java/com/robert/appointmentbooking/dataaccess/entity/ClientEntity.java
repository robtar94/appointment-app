package com.robert.appointmentbooking.dataaccess.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CLIENT")
@Getter
@Setter
public class ClientEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_SEQ_GEN")
	@SequenceGenerator(sequenceName = "CLIENT_SEQ", name = "CLIENT_SEQ_GEN", allocationSize = 100, initialValue = 1)
	private Long id;

	@OneToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private UserEntity user;

	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY, orphanRemoval = true, cascade = {CascadeType.PERSIST,
			CascadeType.REMOVE})
	private List<AppointmentEntity> appointments = new ArrayList<>();
}

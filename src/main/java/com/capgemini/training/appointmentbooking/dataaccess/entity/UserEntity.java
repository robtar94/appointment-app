package com.capgemini.training.appointmentbooking.dataaccess.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "APPLICATION_USER")
@Getter
@Setter
public class UserEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GEN")
	@SequenceGenerator(sequenceName = "USER_SEQ", name = "USER_SEQ_GEN", allocationSize = 100, initialValue = 1)
	private Long id;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "PASSWORD_HASH")
	private String passwordHash;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

}

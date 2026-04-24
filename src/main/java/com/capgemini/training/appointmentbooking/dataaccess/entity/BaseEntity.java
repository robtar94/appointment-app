package com.capgemini.training.appointmentbooking.dataaccess.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@MappedSuperclass
@Getter
public class BaseEntity {

	@Version
	@Setter
	private int version;

	@Column(insertable = true, updatable = false)
	private Instant created;

	@Column(name = "LAST_UPDATED")
	private Instant lastUpdated;

	@PrePersist
	public void prePersist() {
		Instant now = Instant.now();
		this.created = now;
		this.lastUpdated = now;
	}

	@PreUpdate
	public void preUpdate() {
		this.lastUpdated = Instant.now();
	}
}

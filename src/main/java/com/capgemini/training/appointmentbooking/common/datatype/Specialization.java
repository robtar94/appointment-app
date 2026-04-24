package com.capgemini.training.appointmentbooking.common.datatype;

import lombok.Getter;

@Getter
public enum Specialization {

	DENTIST("Dentist"), CARDIOLOGIST("Cardiologist"), PEDIATRICIAN("Pediatrician"), UROLOGIST("Urologist"), NEUROLOGIST(
			"Neurologist"), ORTHOPAEDIST("Orthopaedist");

	private final String name;

	Specialization(String name) {
		this.name = name;
	}

	public static Specialization getByName(String name) {
		for (Specialization s : Specialization.values()) {
			if (s.getName().equals(name)) {
				return s;
			}
		}
		return null;
	}
}

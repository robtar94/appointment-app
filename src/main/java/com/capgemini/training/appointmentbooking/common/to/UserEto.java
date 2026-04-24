package com.capgemini.training.appointmentbooking.common.to;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEto {
	private final Long id;
	private final String email;
	private final String firstName;
	private final String lastName;
	private final String passwordHash;
}

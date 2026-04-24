package com.robert.appointmentbooking.config;

import org.springframework.stereotype.Component;

@Component("accessControl")
public class AccessControl {

	public static final String ROLE_ADMIN = "ADMIN";
	public static final String ROLE_SPECIALIST = "SPECIALIST";
	public static final String ROLE_RECEPTIONIST = "RECEPTIONIST";
}

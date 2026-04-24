package com.robert.appointmentbooking.common;

import org.assertj.core.api.WithAssertions;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class BaseTest implements WithAssertions {

	protected Instant toInstant(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return LocalDateTime.parse(date, formatter).atZone(ZoneId.systemDefault()).toInstant();
	}
}
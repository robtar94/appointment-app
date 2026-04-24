package com.robert.appointmentbooking.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

	@RequestMapping("/")
	String home() {
		return "home.html";
	}

	@RequestMapping("/appointments")
	String appointments() {
		return "appointments.html";
	}

	@RequestMapping("/treatments")
	String treatments() {
		return "treatments.html";
	}

	@RequestMapping("/login")
	String login() {
		return "login.html";
	}

	String index() {
		return "index.html";
	}
}

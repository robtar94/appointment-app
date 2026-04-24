package com.capgemini.training.appointmentbooking.config.roles;

import com.capgemini.training.appointmentbooking.config.AccessControl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@HasRole(AccessControl.ROLE_SPECIALIST)
public @interface IsSpecialist {
}

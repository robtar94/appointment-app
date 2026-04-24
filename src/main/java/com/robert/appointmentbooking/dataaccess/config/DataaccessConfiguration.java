package com.robert.appointmentbooking.dataaccess.config;

import com.robert.appointmentbooking.dataaccess.repository.BaseJpaRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = BaseJpaRepositoryImpl.class, basePackages = "com.robert.appointmentbooking.dataaccess.repository")
public class DataaccessConfiguration {

}

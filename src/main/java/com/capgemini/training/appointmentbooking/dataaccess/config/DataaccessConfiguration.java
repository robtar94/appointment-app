package com.capgemini.training.appointmentbooking.dataaccess.config;

import com.capgemini.training.appointmentbooking.dataaccess.repository.BaseJpaRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(repositoryBaseClass = BaseJpaRepositoryImpl.class, basePackages = "com.capgemini.training.appointmentbooking.dataaccess.repository")
public class DataaccessConfiguration {

}

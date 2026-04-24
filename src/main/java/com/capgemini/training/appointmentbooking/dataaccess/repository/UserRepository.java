package com.capgemini.training.appointmentbooking.dataaccess.repository;

import java.util.List;

import com.capgemini.training.appointmentbooking.dataaccess.entity.UserEntity;

public interface UserRepository extends BaseJpaRepository<UserEntity, Long> {
	List<UserEntity> findAllByEmail(String email);

	UserEntity findByLastName(String lastName);

}
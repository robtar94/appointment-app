package com.robert.appointmentbooking.dataaccess.repository;

import java.util.List;

import com.robert.appointmentbooking.dataaccess.entity.ClientEntity;
import com.robert.appointmentbooking.dataaccess.entity.QClientEntity;
import com.robert.appointmentbooking.dataaccess.entity.QUserEntity;

public interface ClientRepository extends BaseJpaRepository<ClientEntity, Long> {
	default List<ClientEntity> findByName(String firstName, String lastName) {
		QClientEntity client = QClientEntity.clientEntity;
		QUserEntity user = QUserEntity.userEntity;

		return getQueryFactory().selectFrom(client).leftJoin(client.user, user)
				.where(user.firstName.eq(firstName).and(user.lastName.eq(lastName))).fetch();
	}

	default ClientEntity findByUserId(Long userId) {
		QClientEntity client = QClientEntity.clientEntity;
		QUserEntity user = QUserEntity.userEntity;

		return getQueryFactory().selectFrom(client).leftJoin(client.user, user).where(user.id.eq(userId)).fetchOne();
	}
}
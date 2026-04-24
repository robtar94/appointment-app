package com.capgemini.training.appointmentbooking.dataaccess.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class BaseJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseJpaRepository<T, ID> {

	private final EntityManager entityManager;

	private final JPAQueryFactory queryFactory;

	BaseJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Override
	public JPAQueryFactory getQueryFactory() {
		return this.queryFactory;
	}
}
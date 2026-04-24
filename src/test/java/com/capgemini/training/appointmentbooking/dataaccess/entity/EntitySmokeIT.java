package com.capgemini.training.appointmentbooking.dataaccess.entity;

import java.util.Map;

import com.capgemini.training.appointmentbooking.common.BaseDataJpaTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EntitySmokeIT extends BaseDataJpaTest {

	@PersistenceContext
	private EntityManager em;

	@Test
	void loadAllClasses() {
		// given
		Map<Class<? extends BaseEntity>, Integer> classMap = Map.of(UserEntity.class, 8, ClientEntity.class, 4,
				SpecialistEntity.class, 4, TreatmentEntity.class, 12, AppointmentEntity.class, 20);

		// then
		classMap.forEach((entityType,
				expectedCount) -> assertThat(
						em.createQuery("from " + entityType.getSimpleName(), entityType).getResultList())
						.as("Checking record count for " + entityType.getSimpleName()).hasSize(expectedCount));
	}
}

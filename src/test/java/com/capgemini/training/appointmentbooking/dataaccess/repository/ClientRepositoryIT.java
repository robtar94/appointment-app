package com.capgemini.training.appointmentbooking.dataaccess.repository;

import java.util.List;

import com.capgemini.training.appointmentbooking.common.BaseDataJpaTest;
import com.capgemini.training.appointmentbooking.dataaccess.entity.ClientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientRepositoryIT extends BaseDataJpaTest {

	@Autowired
	private ClientRepository clientRepository;

	@Test
	void shouldFindByQueryDSL() {
		// when
		List<ClientEntity> clients = clientRepository.findByName("Stefan", "Kowalski");

		// then
		assertThat(clients).isNotEmpty().hasSize(1);
		assertThat(clients.getFirst().getUser().getFirstName()).isEqualTo("Stefan");
		assertThat(clients.getFirst().getUser().getLastName()).isEqualTo("Kowalski");
	}
}
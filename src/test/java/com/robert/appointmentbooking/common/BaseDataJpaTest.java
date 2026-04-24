package com.robert.appointmentbooking.common;

import com.robert.appointmentbooking.dataaccess.config.DataaccessConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(DataaccessConfiguration.class)
public class BaseDataJpaTest extends BaseTest {
	// Provides:
	// - JPA testing context via @DataJpaTest
	// - Automatic transaction support (and rollback)
	// - Custom repository configuration via @Import
	// - H2 in-memory database (pre-configured)
}
package com.pim.stars.fleet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { FleetTestConfiguration.WithoutPersistence.class })
@ActiveProfiles("WithoutPersistence")
public class FleetConfigurationTest {

	@Test
	public void testThatApplicationContextStarts() {
	}
}

package com.pim.stars.gadget;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { GadgetTestConfiguration.WithoutPersistence.class })
@ActiveProfiles("WithoutPersistence")
public class GadgetConfigurationTest {

	@Test
	public void testThatApplicationContextStarts() {
	}
}

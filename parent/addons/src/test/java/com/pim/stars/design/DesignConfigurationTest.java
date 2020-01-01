package com.pim.stars.design;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { DesignTestConfiguration.WithoutPersistence.class })
@ActiveProfiles("WithoutPersistence")
public class DesignConfigurationTest {

	@Test
	public void testThatApplicationContextStarts() {
	}
}

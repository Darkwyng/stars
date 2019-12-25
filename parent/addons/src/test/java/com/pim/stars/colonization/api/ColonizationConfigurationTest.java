package com.pim.stars.colonization.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.colonization.ColonizationTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ColonizationTestConfiguration.WithoutPersistence.class)
@ActiveProfiles("WithoutPersistence")
public class ColonizationConfigurationTest {

	@Test
	public void testThatApplicationContextStarts() {

	}
}
package com.pim.stars.cargo.api;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.cargo.CargoTestConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CargoTestConfiguration.WithoutPersistence.class)
public class CargoConfigurationTest {

	@Autowired
	private CargoProcessor cargoProcessor;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(cargoProcessor, not(nullValue()));
	}
}
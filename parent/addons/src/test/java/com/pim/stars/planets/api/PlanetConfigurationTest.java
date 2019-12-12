package com.pim.stars.planets.api;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.cargo.api.extensions.CargoDataExtensionPolicy;
import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.planets.PlanetTestConfiguration;
import com.pim.stars.planets.api.extensions.PlanetCargo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PlanetTestConfiguration.WithoutPersistence.class)
public class PlanetConfigurationTest {

	@Autowired
	private PlanetCargo planetCargo;
	@Autowired
	private CargoDataExtensionPolicy<?> cargoDataExtensionPolicyForGeneric;
	@Autowired
	private CargoDataExtensionPolicy<Planet> cargoDataExtensionPolicyForPlanet;

	/** This is how CargoProcessorImp will autowire the policy, so this must work. */
	@Autowired
	private CargoDataExtensionPolicy<Entity<?>> cargoDataExtensionPolicyForEntity;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(planetCargo, not(nullValue()));
		assertThat(planetCargo, sameInstance(cargoDataExtensionPolicyForGeneric));
		assertThat(planetCargo, sameInstance(cargoDataExtensionPolicyForPlanet));
		assertThat(planetCargo, sameInstance(cargoDataExtensionPolicyForEntity));
	}
}

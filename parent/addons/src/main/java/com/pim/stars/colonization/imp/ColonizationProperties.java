package com.pim.stars.colonization.imp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(ColonizationProperties.class)
@ConfigurationProperties(prefix = "colonization")
public class ColonizationProperties {

	private int defaultPlanetCapacity = 10000;
	private int defaultInitialPopulation = 250;

	public int getDefaultPlanetCapacity() {
		return defaultPlanetCapacity;
	}

	public void setDefaultPlanetCapacity(final int defaultPlanetCapacity) {
		this.defaultPlanetCapacity = defaultPlanetCapacity;
	}

	public int getDefaultInitialPopulation() {
		return defaultInitialPopulation;
	}

	public void setDefaultInitialPopulation(final int defaultInitialPopulation) {
		this.defaultInitialPopulation = defaultInitialPopulation;
	}
}

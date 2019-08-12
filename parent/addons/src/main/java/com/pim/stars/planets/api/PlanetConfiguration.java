package com.pim.stars.planets.api;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.dataextension.api.DataExtensionConfiguration;
import com.pim.stars.planets.api.extensions.GameInitializationDataNumberOfPlanets;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.api.extensions.PlanetCargo;
import com.pim.stars.planets.api.extensions.PlanetName;
import com.pim.stars.planets.api.extensions.PlanetOwner;
import com.pim.stars.planets.imp.PlanetProperties;
import com.pim.stars.planets.imp.effects.PlanetGameInitializationPolicy;

public interface PlanetConfiguration {

	@Configuration
	@EnableConfigurationProperties(PlanetProperties.class) // needed, because PlanetProperties has a @PropertySource
	public static class Provided {

		@Bean
		public PlanetGameInitializationPolicy planetGameInitializationPolicy() {
			return new PlanetGameInitializationPolicy();
		}

		@Bean
		public GamePlanetCollection gamePlanetCollection() {
			return new GamePlanetCollection();
		}

		@Bean
		public PlanetName planetName() {
			return new PlanetName();
		}

		@Bean
		public PlanetOwner planetOwner() {
			return new PlanetOwner();
		}

		@Bean
		public PlanetCargo planetCargoDataExtensionPolicy() {
			return new PlanetCargo();
		}

		@Bean
		public GameInitializationDataNumberOfPlanets gameInitializationDataNumberOfPlanets() {
			return new GameInitializationDataNumberOfPlanets();
		}

		@Bean
		public PlanetProperties planetProperties() {
			return new PlanetProperties();
		}
	}

	@Configuration
	@Import({ DataExtensionConfiguration.Complete.class })
	public static class Complete extends Provided {

	}

	public static interface Required {

		public DataExtender dataExtender();
	}
}
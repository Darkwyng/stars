package com.pim.stars.planets.api;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.cargo.api.extensions.CargoDataExtensionPolicy;
import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.dataextension.api.DataExtensionConfiguration;
import com.pim.stars.planets.api.extensions.GameInitializationDataNumberOfPlanets;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.api.extensions.PlanetCargo;
import com.pim.stars.planets.api.extensions.PlanetName;
import com.pim.stars.planets.api.extensions.PlanetOwner;
import com.pim.stars.planets.imp.PlanetProperties;
import com.pim.stars.planets.imp.effects.PlanetGameInitializationPolicy;
import com.pim.stars.planets.imp.policies.transformers.PlanetCollectionGameEntityTransformer;
import com.pim.stars.planets.imp.policies.transformers.PlanetNameEntityTransformer;
import com.pim.stars.planets.imp.policies.transformers.PlanetTurnEntityCreator;
import com.pim.stars.turn.api.TurnConfiguration;
import com.pim.stars.turn.api.TurnCreator;
import com.pim.stars.turn.api.policies.GameEntityTransformer;
import com.pim.stars.turn.api.policies.TurnEntityCreator;

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
		public CargoDataExtensionPolicy<?> planetCargo() {
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

		@Bean
		public GameEntityTransformer<?, ?> planetCollectionGameEntityTransformer() {
			return new PlanetCollectionGameEntityTransformer();
		}

		@Bean
		public GameEntityTransformer<?, ?> planetNameEntityTransformer() {
			return new PlanetNameEntityTransformer();
		}

		@Bean
		public TurnEntityCreator<?> planetTurnEntityCreator() {
			return new PlanetTurnEntityCreator();
		}
	}

	@Configuration
	@Import({ DataExtensionConfiguration.Complete.class, TurnConfiguration.Complete.class })
	public static class Complete extends Provided {

	}

	public static interface Required {

		public DataExtender dataExtender();

		public TurnCreator turnCreator();
	}
}
package com.pim.stars.planets.api;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.cargo.api.Cargo;
import com.pim.stars.cargo.api.extensions.CargoDataExtensionPolicy;
import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.dataextension.api.DataExtensionConfiguration;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.planets.api.extensions.GameInitializationDataNumberOfPlanets;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.api.extensions.PlanetCargo;
import com.pim.stars.planets.api.extensions.PlanetName;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;
import com.pim.stars.planets.imp.PlanetImp;
import com.pim.stars.planets.imp.PlanetProperties;
import com.pim.stars.planets.imp.effects.PlanetEffectHolderProviderPolicy;
import com.pim.stars.planets.imp.effects.PlanetGameInitializationPolicy;
import com.pim.stars.race.api.RaceConfiguration;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.race.api.extensions.RaceId;
import com.pim.stars.turn.api.TurnConfiguration;
import com.pim.stars.turn.api.policies.GameEntityTransformer;
import com.pim.stars.turn.api.policies.TurnEntityCreator;
import com.pim.stars.turn.api.policies.builder.GameToTurnTransformerBuilder;

public interface PlanetConfiguration {

	@Configuration
	@EnableConfigurationProperties(PlanetProperties.class) // needed, because PlanetProperties has a @PropertySource
	public static class Provided {

		@Bean
		public PlanetGameInitializationPolicy planetGameInitializationPolicy() {
			return new PlanetGameInitializationPolicy();
		}

		@Bean
		public PlanetEffectHolderProviderPolicy planetEffectHolderProviderPolicy() {
			return new PlanetEffectHolderProviderPolicy();
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
		public PlanetOwnerId planetOwnerId() {
			return new PlanetOwnerId();
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
		public GameEntityTransformer<?, ?> planetCollectionGameEntityTransformer(
				final GameToTurnTransformerBuilder builder) {
			return builder.transformEntityCollectionExtension(GamePlanetCollection.class).build();
		}

		@Bean
		public TurnEntityCreator<?> planetTurnEntityCreator(final GameToTurnTransformerBuilder builder) {
			return builder.transformEntity(Planet.class).build((entity, race) -> new PlanetImp());
		}

		@Bean
		public GameEntityTransformer<?, ?> planetNameEntityTransformer(final GameToTurnTransformerBuilder builder) {
			return builder.transformExtension(PlanetName.class).copyAll().build();
		}

		@Bean
		public GameEntityTransformer<?, ?> planetOwnerEntityTransformer(final GameToTurnTransformerBuilder builder,
				final RaceId raceId) {
			return builder.transformExtension(PlanetOwnerId.class) //
					.transform((ownerId, context) -> {
						// Copy, if the owner of the planet is the owner of the turn:
						if (ownerId == null) {
							return null;
						} else {
							final String turnOwnerId = raceId.getValue(context.getRace());
							return turnOwnerId.equals(ownerId) ? ownerId : null;
						}
					}).build();
		}

		@Bean
		public GameEntityTransformer<?, ?> planetCargoEntityTransformer(final GameToTurnTransformerBuilder builder,
				final RaceId raceId, final PlanetOwnerId planetOwnerId) {
			return builder.transformExtension(PlanetCargo.class).transform((cargo, context) -> {
				// Copy, if the owner of the planet is the owner of the turn:
				final String ownerId = planetOwnerId.getValue((Planet) context.getGameEntityStack().peek());
				if (ownerId == null) {
					return cargo;
				} else {
					final String turnOwnerId = raceId.getValue(context.getRace());
					return turnOwnerId.equals(ownerId) ? cargo : new Cargo();
				}
			}).build();
		}

	}

	@Configuration
	@Import({ DataExtensionConfiguration.Complete.class, TurnConfiguration.Complete.class,
			RaceConfiguration.Complete.class })
	public static class Complete extends Provided {

	}

	public static interface Required {

		public DataExtender dataExtender();

		public GameToTurnTransformerBuilder gameToTurnTransformerBuilder();

		public GameRaceCollection gameRaceCollection();

		public RaceId raceId();

		public IdCreator idCreator();

	}
}
package com.pim.stars.race;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import com.pim.stars.dataextension.DataExtensionConfiguration;
import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.id.IdConfiguration;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.race.api.extensions.RaceId;
import com.pim.stars.race.api.extensions.RacePrimaryRacialTrait;
import com.pim.stars.race.api.extensions.RaceSecondaryRacialTraitCollection;
import com.pim.stars.race.imp.RaceImp;
import com.pim.stars.turn.TurnConfiguration;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.policies.GameEntityTransformer;
import com.pim.stars.turn.api.policies.TurnEntityCreator;
import com.pim.stars.turn.api.policies.builder.GameToTurnTransformerBuilder;

public interface RaceConfiguration {

	@Configuration
	@ComponentScan(excludeFilters = {
			@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = Complete.class) })
	public static class Provided {

		@Bean
		public TurnEntityCreator<?> raceTurnEntityCreator(final GameToTurnTransformerBuilder builder) {
			return builder.transformEntity(Race.class).build((entity, race) -> new RaceImp());
		}

		@Bean
		public GameEntityTransformer<?, ?> gameRaceCollectionEntityTransformer(
				final GameToTurnTransformerBuilder builder) {
			return builder.transformEntityCollectionExtension(GameRaceCollection.class).build();
		}

		@Bean
		public GameEntityTransformer<?, ?> raceIdEntityTransformer(final GameToTurnTransformerBuilder builder) {
			return builder.transformExtension(RaceId.class).copyAll().build();
		}

		@Bean
		public GameEntityTransformer<?, ?> racePrimaryRacialTraitEntityTransformer(
				final GameToTurnTransformerBuilder builder) {
			return builder.transformExtension(RacePrimaryRacialTrait.class).transform((trait, context) -> {
				// If the turn is for the race that is being transformed,...
				if (context.getGameEntityStack().peek() == context.getRace()) {
					return trait; // ... copy the trait,...
				} else {
					return null; // ... otherwise hide it.
				}
			}).build();
		}

		@Bean
		public GameEntityTransformer<?, ?> secondaryRacialTraitCollectionEntityTransformer(
				final GameToTurnTransformerBuilder builder) {
			return builder.transformExtension(RaceSecondaryRacialTraitCollection.class).transform((traits, context) -> {
				// If the turn is for the race that is being transformed,...
				if (context.getGameEntityStack().peek() == context.getRace()) {
					return traits; // ... copy the traits,...
				} else {
					return new ArrayList<>(); // ... otherwise hide them.
				}
			}).build();
		}
	}

	@Configuration
	@Import({ IdConfiguration.Complete.class, DataExtensionConfiguration.Complete.class,
			TurnConfiguration.Complete.class })
	public static class Complete extends Provided {

	}

	public static interface Required {

		public IdCreator idCreator();

		public DataExtender dataExtender();

		public GameToTurnTransformerBuilder gameToTurnTransformerBuilder();
	}
}
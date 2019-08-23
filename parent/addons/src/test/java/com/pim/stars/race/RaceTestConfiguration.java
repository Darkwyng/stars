package com.pim.stars.race;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.race.api.RaceConfiguration;
import com.pim.stars.turn.api.policies.GameEntityTransformer;
import com.pim.stars.turn.api.policies.TurnEntityCreator;
import com.pim.stars.turn.api.policies.builder.GameToTurnTransformerBuilder;

@Configuration
@Import({ RaceConfiguration.Provided.class })
public class RaceTestConfiguration implements RaceConfiguration.Required {

	@Bean
	@Override
	public IdCreator idCreator() {
		return mock(IdCreator.class);
	}

	@Bean
	@Override
	public DataExtender dataExtender() {
		return mock(DataExtender.class);
	}

	@Bean
	@Override
	public GameToTurnTransformerBuilder gameToTurnTransformerBuilder() {
		return mock(GameToTurnTransformerBuilder.class);
	}

	@Bean
	@Primary
	public TurnEntityCreator<?> raceTurnEntityCreator() {
		// marked as @Primary and mocked, so that the builder passed into the real method in the real configuration does not have to be mocked.
		return mock(TurnEntityCreator.class);
	}

	@Bean
	@Primary
	public GameEntityTransformer<?, ?> gameRaceCollectionEntityTransformer() {
		// marked as @Primary and mocked, so that the builder passed into the real method in the real configuration does not have to be mocked.
		return mock(GameEntityTransformer.class);
	}

	@Bean
	@Primary
	public GameEntityTransformer<?, ?> raceIdEntityTransformer() {
		// marked as @Primary and mocked, so that the builder passed into the real method in the real configuration does not have to be mocked.
		return mock(GameEntityTransformer.class);
	}

	@Bean
	@Primary
	public GameEntityTransformer<?, ?> racePrimaryRacialTraitEntityTransformer() {
		// marked as @Primary and mocked, so that the builder passed into the real method in the real configuration does not have to be mocked.
		return mock(GameEntityTransformer.class);
	}

	@Bean
	@Primary
	public GameEntityTransformer<?, ?> secondaryRacialTraitCollectionEntityTransformer() {
		// marked as @Primary and mocked, so that the builder passed into the real method in the real configuration does not have to be mocked.
		return mock(GameEntityTransformer.class);
	}
}
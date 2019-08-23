package com.pim.stars.planets;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.planets.api.PlanetConfiguration;
import com.pim.stars.race.api.extensions.RaceId;
import com.pim.stars.turn.api.policies.GameEntityTransformer;
import com.pim.stars.turn.api.policies.TurnEntityCreator;
import com.pim.stars.turn.api.policies.builder.GameToTurnTransformerBuilder;

@Configuration
@Import({ PlanetConfiguration.Provided.class })
public class PlanetTestConfiguration implements PlanetConfiguration.Required {

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
	public TurnEntityCreator<?> planetTurnEntityCreator() {
		// marked as @Primary and mocked, so that the builder passed into the real method in the real configuration does not have to be mocked.
		return mock(TurnEntityCreator.class);
	}

	@Bean
	@Primary
	public GameEntityTransformer<?, ?> planetCollectionGameEntityTransformer() {
		// marked as @Primary and mocked, so that the builder passed into the real method in the real configuration does not have to be mocked.
		return mock(GameEntityTransformer.class);
	}

	@Bean
	@Primary
	public GameEntityTransformer<?, ?> planetNameEntityTransformer() {
		// marked as @Primary and mocked, so that the builder passed into the real method in the real configuration does not have to be mocked.
		return mock(GameEntityTransformer.class);
	}

	@Bean
	@Primary
	public GameEntityTransformer<?, ?> planetOwnerEntityTransformer(final GameToTurnTransformerBuilder builder) {
		// marked as @Primary and mocked, so that the builder passed into the real method in the real configuration does not have to be mocked.
		return mock(GameEntityTransformer.class);
	}

	@Bean
	@Primary
	public GameEntityTransformer<?, ?> planetCargoEntityTransformer(final GameToTurnTransformerBuilder builder) {
		// marked as @Primary and mocked, so that the builder passed into the real method in the real configuration does not have to be mocked.
		return mock(GameEntityTransformer.class);
	}

	@Bean
	@Override
	public RaceId raceId() {
		return mock(RaceId.class);
	}

	@Bean
	@Override
	public IdCreator idCreator() {
		return mock(IdCreator.class);
	}
}
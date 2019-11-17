package com.pim.stars.planets;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import org.mockito.Answers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.race.api.RaceProvider;
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
		return mock(GameToTurnTransformerBuilder.class, withSettings().defaultAnswer(Answers.RETURNS_DEEP_STUBS));
	}

	@Bean
	@Override
	public IdCreator idCreator() {
		return mock(IdCreator.class);
	}

	@Bean
	@Override
	public RaceProvider raceProvider() {
		return mock(RaceProvider.class);
	}
}
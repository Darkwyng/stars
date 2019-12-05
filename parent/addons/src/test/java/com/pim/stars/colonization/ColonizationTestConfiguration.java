package com.pim.stars.colonization;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;
import com.pim.stars.race.api.RaceProvider;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;

@Configuration
@Import({ ColonizationConfiguration.Provided.class })
public class ColonizationTestConfiguration implements ColonizationConfiguration.Required {

	@Bean
	@Override
	public GamePlanetCollection gamePlanetCollection() {
		return mock(GamePlanetCollection.class);
	}

	@Bean
	@Override
	public PlanetOwnerId planetOwnerId() {
		return mock(PlanetOwnerId.class);
	}

	@Bean
	@Override
	public CargoProcessor cargoProcessor() {
		return mock(CargoProcessor.class);
	}

	@Bean
	@Override
	public EffectCalculator effectCalculator() {
		return mock(EffectCalculator.class);
	}

	@Bean
	@Override
	public EffectExecutor effectExecutor() {
		return mock(EffectExecutor.class);
	}

	@Bean
	@Override
	public IdCreator idCreator() {
		return mock(IdCreator.class);
	}

	@Override
	@Bean
	public GameInitializationDataRaceCollection gameInitializationDataRaceCollection() {
		return mock(GameInitializationDataRaceCollection.class);
	}

	@Override
	@Bean
	public RaceProvider raceProvider() {
		return mock(RaceProvider.class);
	}
}
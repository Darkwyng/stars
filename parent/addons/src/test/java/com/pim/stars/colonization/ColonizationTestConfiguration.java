package com.pim.stars.colonization;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.api.ColonizationConfiguration;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.api.extensions.PlanetOwner;
import com.pim.stars.race.api.extensions.GameRaceCollection;

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
	public GameRaceCollection gameRaceCollection() {
		return mock(GameRaceCollection.class);
	}

	@Bean
	@Override
	public PlanetOwner planetOwner() {
		return mock(PlanetOwner.class);
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
}
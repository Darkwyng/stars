package com.pim.stars.mineral;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import org.mockito.Answers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.api.extensions.PlanetName;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;
import com.pim.stars.production.api.ProductionAvailabilityCalculator;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.report.api.ReportCreator;
import com.pim.stars.resource.api.policies.ResourceProductionCostType;

@Configuration
@Import({ MineralConfiguration.Provided.class })
public class MineralTestConfiguration implements MineralConfiguration.Required {

	@Bean
	@Override
	public EffectCalculator effectCalculator() {
		return mock(EffectCalculator.class);
	}

	@Bean
	@Override
	public CargoProcessor cargoProcessor() {
		return mock(CargoProcessor.class);
	}

	@Bean
	@Override
	public PlanetOwnerId planetOwnerId() {
		return mock(PlanetOwnerId.class);
	}

	@Bean
	@Override
	public PlanetName planetName() {
		return mock(PlanetName.class);
	}

	@Bean
	@Override
	public GameRaceCollection gameRaceCollection() {
		return mock(GameRaceCollection.class);
	}

	@Bean
	@Override
	public ProductionAvailabilityCalculator productionAvailabilityCalculator() {
		return mock(ProductionAvailabilityCalculator.class);
	}

	@Bean
	@Override
	public GamePlanetCollection gamePlanetCollection() {
		return mock(GamePlanetCollection.class);
	}

	@Bean
	@Override
	public IdCreator idCreator() {
		return mock(IdCreator.class);
	}

	@Bean
	@Override
	public ReportCreator reportCreator() {
		return mock(ReportCreator.class, withSettings().defaultAnswer(Answers.RETURNS_DEEP_STUBS));
	}

	@Bean
	public ResourceProductionCostType resourceProductionCostType() {
		return mock(ResourceProductionCostType.class);
	}

}
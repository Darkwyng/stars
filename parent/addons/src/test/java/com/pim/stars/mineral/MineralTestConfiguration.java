package com.pim.stars.mineral;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.mockito.Answers;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.cargo.api.policies.CargoType;
import com.pim.stars.cargo.api.policies.CargoType.CargoTypeFactory;
import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetRepository;
import com.pim.stars.mineral.imp.persistence.race.MineralRaceRepository;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.production.api.ProductionAvailabilityCalculator;
import com.pim.stars.production.api.policies.ProductionCostType;
import com.pim.stars.production.api.policies.ProductionCostType.ProductionCostTypeFactory;
import com.pim.stars.race.api.RaceProvider;
import com.pim.stars.race.api.extensions.GameInitializationDataRaceCollection;
import com.pim.stars.report.api.ReportCreator;
import com.pim.stars.resource.api.ResourceProductionCostTypeProvider;

public class MineralTestConfiguration implements MineralConfiguration.Required {

	/**
	 * Copied from CargoConfiguration: To test that mineral types can also be autowired as cargo types.
	 */
	@Bean
	public List<CargoType> cargoTypes(final Collection<CargoTypeFactory> factories) {
		return factories.stream().map(CargoTypeFactory::createCargoTypes).flatMap(Collection<CargoType>::stream)
				.collect(Collectors.toList());
	}

	/**
	 * Copied from ProductionConfiguration: To test that mineral types can also be autowired as production cost types.
	 */
	@Bean
	public List<ProductionCostType> productionCostTypes(final Collection<ProductionCostTypeFactory> factories) {
		return factories.stream().map(ProductionCostTypeFactory::createProductionCostTypes)
				.flatMap(Collection<ProductionCostType>::stream).collect(Collectors.toList());
	}

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
	public ProductionAvailabilityCalculator productionAvailabilityCalculator() {
		return mock(ProductionAvailabilityCalculator.class);
	}

	@Override
	@Bean
	public GameInitializationDataRaceCollection gameInitializationDataRaceCollection() {
		return mock(GameInitializationDataRaceCollection.class);
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

	@Override
	@Bean
	public RaceProvider raceProvider() {
		return mock(RaceProvider.class);
	}

	@Override
	@Bean
	public PlanetProvider planetProvider() {
		return mock(PlanetProvider.class);
	}

	@Override
	@Bean
	public ResourceProductionCostTypeProvider resourceProductionCostTypeProvider() {
		return mock(ResourceProductionCostTypeProvider.class);
	}

	@Configuration
	@Import({ MineralTestConfiguration.class, MineralConfiguration.Provided.class })
	public class WithoutPersistence {

		@MockBean
		private MineralRaceRepository mineralRaceRepository;
		@MockBean
		private MineralPlanetRepository mineralPlanetRepository;
	}

	@Configuration
	@EnableAutoConfiguration // Required by @DataMongoTest
	@DataMongoTest
	@Import({ MineralTestConfiguration.class, MineralConfiguration.Provided.class })
	public class WithPersistence {

	}
}
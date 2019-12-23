package com.pim.stars.colonization.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.api.ColonistCargoTypeProvider;
import com.pim.stars.colonization.api.effects.PlanetColonizationPolicy;
import com.pim.stars.colonization.imp.ColonizationProperties;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.effects.HomeworldInitializationPolicy;
import com.pim.stars.race.api.Race;

@Component
public class ColonistHomeworldInitializationPolicy implements HomeworldInitializationPolicy {

	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private ColonistCargoTypeProvider colonistCargoTypeProvider;
	@Autowired
	private ColonizationProperties colonizationProperties;
	@Autowired
	private EffectExecutor effectExecutor;

	@Override
	public void initializeHomeworld(final Game game, final Planet planet, final Race race,
			final GameInitializationData data) {

		cargoProcessor.createCargoHolder(game, planet).transferFromNowhere()
				.quantity(colonistCargoTypeProvider.getColonistCargoType(), getInitialPopulation()).execute();

		effectExecutor.executeEffect(game, PlanetColonizationPolicy.class, planet,
				(policy, context) -> policy.colonizePlanet(game, planet, race));
	}

	public int getInitialPopulation() {
		return colonizationProperties.getDefaultInitialPopulation();
	}
}
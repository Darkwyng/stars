package com.pim.stars.colonization.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.cargo.api.CargoProcessor;
import com.pim.stars.colonization.api.policies.ColonistCargoType;
import com.pim.stars.colonization.imp.ColonizationProperties;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.effects.HomeworldInitializationPolicy;

public class ColonistHomeworldInitializationPolicy implements HomeworldInitializationPolicy {

	@Autowired
	private CargoProcessor cargoProcessor;
	@Autowired
	private ColonistCargoType colonistCargoType;

	@Autowired
	private ColonizationProperties colonizationProperties;

	@Override
	public void initializeHomeworld(final Game game, final Planet planet, final GameInitializationData data) {
		cargoProcessor.createCargoHolder(planet).transferFromNowhere()
				.quantity(colonistCargoType, getInitialPopulation()).execute();
	}

	public int getInitialPopulation() {
		return colonizationProperties.getDefaultInitialPopulation();
	}
}
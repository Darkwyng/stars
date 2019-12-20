package com.pim.stars.mineral.imp.effects;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.effects.GameInitializationPolicy;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetPersistenceInterface;
import com.pim.stars.planets.api.PlanetProvider;

@Component
public class MineralConcentrationGameInitializationPolicy implements GameInitializationPolicy {

	private static final Random RANDOM = new Random();

	@Autowired
	private PlanetProvider planetProvider;
	@Autowired
	private MineralPlanetPersistenceInterface mineralPlanetPersistenceInterface;

	@Override
	public int getSequence() {
		return 3000;
	}

	@Override
	public void initializeGame(final Game game, final GameInitializationData initializationData) {
		mineralPlanetPersistenceInterface.createPlanetsWithConcentrations(game, planetProvider.getPlanetsByGame(game),
				type -> 1.2 * RANDOM.nextInt(100));

	}
}
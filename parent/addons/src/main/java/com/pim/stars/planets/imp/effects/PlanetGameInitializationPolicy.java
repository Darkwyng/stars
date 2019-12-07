package com.pim.stars.planets.imp.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.effects.GameInitializationPolicy;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.GameInitializationDataNumberOfPlanets;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.imp.PlanetImp;
import com.pim.stars.planets.imp.PlanetProperties;

@Component
public class PlanetGameInitializationPolicy implements GameInitializationPolicy {

	@Autowired
	private GamePlanetCollection gamePlanetCollection;
	@Autowired
	private GameInitializationDataNumberOfPlanets gameInitializationDataNumberOfPlanets;

	@Autowired
	private DataExtender dataExtender;
	@Autowired
	private PlanetProperties planetProperties;

	final Random random = new Random();

	@Override
	public int getSequence() {
		return 2000;
	}

	@Override
	public void initializeGame(final Game game, final GameInitializationData data) {
		final List<String> availableNames = getAvailablePlanetNames(data);

		final Collection<Planet> planetCollection = gamePlanetCollection.getValue(game);
		for (int i = 0; i < gameInitializationDataNumberOfPlanets.getValue(data); i++) {
			final String name = selectNewPlanetName(availableNames);
			final Planet newPlanet = dataExtender.extendData(new PlanetImp(name));

			planetCollection.add(newPlanet);
		}
	}

	private List<String> getAvailablePlanetNames(final GameInitializationData data) {
		final List<String> names = new ArrayList<>(planetProperties.getNames());
		Assert.isTrue(names.size() >= gameInitializationDataNumberOfPlanets.getValue(data),
				"There must be at least as many planet names as planets.");
		return names;
	}

	private String selectNewPlanetName(final List<String> availableNames) {
		final int index = random.nextInt(availableNames.size());
		return availableNames.remove(index);
	}
}
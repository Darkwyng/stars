package com.pim.stars.planets.imp.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.effects.GameInitializationPolicy;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.GameInitializationDataNumberOfPlanets;
import com.pim.stars.planets.imp.PlanetImp;
import com.pim.stars.planets.imp.PlanetProperties;
import com.pim.stars.planets.imp.persistence.PlanetEntity;
import com.pim.stars.planets.imp.persistence.PlanetRepository;

@Component
public class PlanetGameInitializationPolicy implements GameInitializationPolicy {

	@Autowired
	private GameInitializationDataNumberOfPlanets gameInitializationDataNumberOfPlanets;
	@Autowired
	private PlanetProperties planetProperties;
	@Autowired
	private PlanetRepository planetRepository;

	final Random random = new Random();

	@Override
	public int getSequence() {
		return 2000;
	}

	@Override
	public void initializeGame(final Game game, final GameInitializationData data) {
		final List<String> availableNames = getAvailablePlanetNames(data);

		final Collection<Planet> newPlanetCollection = new ArrayList<>();
		for (int i = 0; i < gameInitializationDataNumberOfPlanets.getValue(data); i++) {
			final String name = selectNewPlanetName(availableNames);
			final Planet newPlanet = new PlanetImp(name, null);

			newPlanetCollection.add(newPlanet);
		}
		persistPlanetCollection(game, newPlanetCollection);
	}

	protected List<String> getAvailablePlanetNames(final GameInitializationData data) {
		final List<String> names = new ArrayList<>(planetProperties.getNames());
		Assert.isTrue(names.size() >= gameInitializationDataNumberOfPlanets.getValue(data),
				"There must be at least as many planet names as planets.");
		return names;
	}

	private String selectNewPlanetName(final List<String> availableNames) {
		final int index = random.nextInt(availableNames.size());
		return availableNames.remove(index);
	}

	private void persistPlanetCollection(final Game game, final Collection<Planet> planetCollection) {
		final List<PlanetEntity> entityCollection = planetCollection.stream().map(planet -> {
			final PlanetEntity entity = new PlanetEntity();
			entity.getEntityId().setGameId(game.getId());
			entity.getEntityId().setYear(game.getYear());
			entity.getEntityId().setName(planet.getName());

			return entity;
		}).collect(Collectors.toList());

		planetRepository.saveAll(entityCollection);
	}
}
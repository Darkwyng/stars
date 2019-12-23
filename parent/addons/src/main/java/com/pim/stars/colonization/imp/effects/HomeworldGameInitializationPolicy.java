package com.pim.stars.colonization.imp.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.effects.GameInitializationPolicy;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProcessor;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.planets.api.effects.HomeworldInitializationPolicy;
import com.pim.stars.race.api.Race;
import com.pim.stars.race.api.RaceProvider;

@Component
public class HomeworldGameInitializationPolicy implements GameInitializationPolicy {

	@Autowired
	private PlanetProvider planetProvider;
	@Autowired
	private EffectExecutor effectExecutor;
	@Autowired
	private RaceProvider raceProvider;
	@Autowired
	private PlanetProcessor planetProcessor;

	private final Random random = new Random();

	@Override
	public int getSequence() {
		return 4000;
	}

	@Override
	public void initializeGame(final Game game, final GameInitializationData initializationData) {
		final Collection<Planet> planetCollection = planetProvider.getPlanetsByGame(game).collect(Collectors.toList());
		final Collection<Planet> homeworlds = new ArrayList<>();
		raceProvider.getRacesByGame(game).forEach(race -> {
			Planet planet = selectNewHomeworld(planetCollection, homeworlds);
			planet = planetProcessor.setPlanetOwnerId(game, planet, race.getId());

			initializeHomeworld(game, initializationData, race, planet);
		});
	}

	private void initializeHomeworld(final Game game, final GameInitializationData initializationData, final Race race,
			final Planet planet) {
		effectExecutor.executeEffect(game, HomeworldInitializationPolicy.class, planet, (policy, context) -> {
			policy.initializeHomeworld(game, planet, race, initializationData);
		});
	}

	protected Planet selectNewHomeworld(final Collection<Planet> planetCollection,
			final Collection<Planet> homeworlds) {
		Assert.isTrue(planetCollection.size() > homeworlds.size(), "There must be enough planets for all races.");

		final List<Planet> planetList = new ArrayList<>(planetCollection);
		while (true) {
			final int index = random.nextInt(planetList.size());
			final Planet newHomeworld = planetList.get(index);
			if (!homeworlds.contains(newHomeworld)) {
				homeworlds.add(newHomeworld);
				return newHomeworld;
			}
		}
	}
}
package com.pim.stars.colonization.imp.effects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.effects.GameInitializationPolicy;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.effects.HomeworldInitializationPolicy;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.planets.api.extensions.PlanetOwnerId;
import com.pim.stars.race.api.extensions.GameRaceCollection;
import com.pim.stars.race.api.extensions.RaceId;

public class HomeworldGameInitializationPolicy implements GameInitializationPolicy {

	@Autowired
	private EffectExecutor effectExecutor;
	@Autowired
	private GameRaceCollection gameRaceCollection;
	@Autowired
	private GamePlanetCollection gamePlanetCollection;

	@Autowired
	private PlanetOwnerId planetOwnerId;
	@Autowired
	private RaceId raceId;

	final Random random = new Random();

	@Override
	public int getSequence() {
		return 4000;
	}

	@Override
	public void initializeGame(final Game game, final GameInitializationData initializationData) {
		final Collection<Planet> planetCollection = gamePlanetCollection.getValue(game);
		final Collection<Planet> homeworlds = new ArrayList<>();
		gameRaceCollection.getValue(game).forEach(race -> {
			final Planet planet = selectNewHomeworld(planetCollection, homeworlds);
			final String ownerId = raceId.getValue(race);
			planetOwnerId.setValue(planet, ownerId);

			effectExecutor.executeEffect(game, HomeworldInitializationPolicy.class, planet,
					(policy, context) -> policy.initializeHomeworld(game, planet, race, initializationData));
		});
	}

	private Planet selectNewHomeworld(final Collection<Planet> planetCollection, final Collection<Planet> homeworlds) {
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
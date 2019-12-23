package com.pim.stars.colonization.api.effects;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.race.api.Race;

public interface PlanetColonizationPolicy extends Effect {

	public void colonizePlanet(Game game, Planet planet, Race race);
}

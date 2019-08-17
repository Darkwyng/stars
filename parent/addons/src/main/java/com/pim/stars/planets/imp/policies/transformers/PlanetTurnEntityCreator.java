package com.pim.stars.planets.imp.policies.transformers;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.imp.PlanetImp;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.policies.TurnEntityCreator;

public class PlanetTurnEntityCreator implements TurnEntityCreator<Planet> {

	@Override
	public Class<Planet> getEntityClass() {
		return Planet.class;
	}

	@Override
	public Entity<?> createTurnEntity(final Planet gameEntity, final Race race) {
		return new PlanetImp();
	}
}

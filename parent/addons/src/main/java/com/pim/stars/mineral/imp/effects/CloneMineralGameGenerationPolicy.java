package com.pim.stars.mineral.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.effects.GameGenerationPolicy;
import com.pim.stars.mineral.imp.persistence.planet.MineralPlanetPersistenceInterface;

@Component
public class CloneMineralGameGenerationPolicy implements GameGenerationPolicy {

	@Autowired
	private MineralPlanetPersistenceInterface mineralPlanetPersistenceInterface;

	@Override
	public int getSequence() {
		return 10;
	}

	@Override
	public void generateGame(final GameGenerationContext context) {
		mineralPlanetPersistenceInterface.cloneEntitiesOfPreviousYear(context.getPreviousYear(),
				context.getCurrentYear());
	}
}

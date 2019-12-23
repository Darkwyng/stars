package com.pim.stars.production.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.effects.GameGenerationPolicy;
import com.pim.stars.production.imp.persistence.ProductionPersistenceInterface;

@Component
public class CloneProductionGameGenerationPolicy implements GameGenerationPolicy {

	@Autowired
	private ProductionPersistenceInterface productionPersistenceInterface;

	@Override
	public int getSequence() {
		return 10;
	}

	@Override
	public void generateGame(final GameGenerationContext context) {
		productionPersistenceInterface.cloneEntitiesOfPreviousYear(context.getPreviousYear(), context.getCurrentYear());
	}
}

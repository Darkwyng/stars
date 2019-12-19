package com.pim.stars.cargo.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.cargo.imp.persistence.CargoPersistenceInterface;
import com.pim.stars.game.api.effects.GameGenerationPolicy;

@Component
public class CloneCargoGameGenerationPolicy implements GameGenerationPolicy {

	@Autowired
	private CargoPersistenceInterface cargoPersistenceInterface;

	@Override
	public int getSequence() {
		return 10;
	}

	@Override
	public void generateGame(final GameGenerationContext context) {
		cargoPersistenceInterface.cloneEntitiesOfPreviousYear(context.getPreviousYear(), context.getCurrentYear());
	}
}

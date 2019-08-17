package com.pim.stars.turn.imp.policies;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.game.api.Game;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.policies.TurnEntityCreator;
import com.pim.stars.turn.imp.TurnImp;

public class GameTurnEntityCreator implements TurnEntityCreator<Game> {

	@Override
	public Class<Game> getEntityClass() {
		return Game.class;
	}

	@Override
	public Entity<?> createTurnEntity(final Game gameEntity, final Race race) {
		return new TurnImp();
	}
}

package com.pim.stars.turn.api;

import java.util.Optional;
import java.util.Stack;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.game.api.Game;

public interface TurnCreator {

	public Turn createTurn(Game game, Race race);

	public Optional<Entity<?>> transformGameEntity(Entity<?> gameEntity, TurnTransformationContext context);

	public interface TurnTransformationContext {

		public Race getRace();

		public Stack<Entity<?>> getGameEntityStack();
	}
}

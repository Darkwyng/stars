package com.pim.stars.turn.api.policies;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.turn.api.Race;

public interface TurnEntityCreator<E extends Entity<?>> {

	public Class<E> getEntityClass();

	public Entity<?> createTurnEntity(E gameEntity, Race race);
}

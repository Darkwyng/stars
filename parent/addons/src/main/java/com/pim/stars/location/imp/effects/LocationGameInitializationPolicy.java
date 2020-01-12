package com.pim.stars.location.imp.effects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.effects.GameInitializationPolicy;
import com.pim.stars.location.api.extensions.GameInitializationDataUniverseSize;
import com.pim.stars.location.api.extensions.GameInitializationDataUniverseSize.UniverseSize;
import com.pim.stars.location.imp.persistence.universe.UniverseEntity;
import com.pim.stars.location.imp.persistence.universe.UniverseRepository;

@Component
public class LocationGameInitializationPolicy implements GameInitializationPolicy {

	@Autowired
	private GameInitializationDataUniverseSize gameInitializationDataUniverseSize;
	@Autowired
	private UniverseRepository universeRepository;

	@Override
	public int getSequence() {
		return 0;
	}

	@Override
	public void initializeGame(final Game game, final GameInitializationData initializationData) {
		final UniverseSize size = gameInitializationDataUniverseSize.getValue(initializationData);
		final UniverseEntity entity = new UniverseEntity(game.getId(), size.getMaxX(), size.getMaxY());
		universeRepository.save(entity);
	}
}
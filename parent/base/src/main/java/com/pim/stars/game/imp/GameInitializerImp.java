package com.pim.stars.game.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.game.api.effects.GameInitializationPolicy;
import com.pim.stars.game.imp.persistence.GamePersistenceInterface;
import com.pim.stars.id.api.IdCreator;

@Component
public class GameInitializerImp implements GameInitializer {

	@Autowired
	private EffectExecutor effectExecutor;
	@Autowired
	private DataExtender dataExtender;
	@Autowired
	private IdCreator idCreator;
	@Autowired
	private GameProperties gameProperties;
	@Autowired
	private GamePersistenceInterface gamePersistenceInterface;

	@Override
	public GameInitializationData createNewGameInitializationData() {
		return dataExtender.extendData(new GameInitializationDataImp());
	}

	@Override
	public Game initializeGame(final GameInitializationData data) {
		final Game game = new GameImp(idCreator.createId(), gameProperties.getStartingYear());

		effectExecutor.executeEffect(game, GameInitializationPolicy.class, null,
				(policy, effectContext) -> policy.initializeGame(game, data));

		gamePersistenceInterface.initializeGame(game.getId(), game.getYear());
		return game;
	}
}
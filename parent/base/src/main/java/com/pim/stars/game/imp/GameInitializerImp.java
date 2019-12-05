package com.pim.stars.game.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.effect.api.EffectProvider;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.game.api.effects.GameInitializationPolicy;
import com.pim.stars.game.imp.persistence.GamePersistenceInterface;
import com.pim.stars.id.api.IdCreator;

@Component
public class GameInitializerImp implements GameInitializer {

	@Autowired
	private EffectProvider effectProvider;
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
		final Game game = dataExtender.extendData(new GameImp(idCreator.createId(), gameProperties.getStartingYear()));

		effectProvider.getEffectCollection(game, null, GameInitializationPolicy.class).stream()
				.forEach(policy -> policy.initializeGame(game, data));

		gamePersistenceInterface.initializeGame(game.getId(), game.getYear());
		return game;
	}
}
package com.pim.stars.game.imp;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.effect.api.EffectProvider;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.game.api.effects.GameInitializationPolicy;

public class GameInitializerImp implements GameInitializer {

	@Autowired
	private EffectProvider effectProvider;
	@Autowired
	private DataExtender dataExtender;

	@Override
	public GameInitializationData createNewGameInitializationData() {
		final GameInitializationDataImp data = new GameInitializationDataImp();

		dataExtender.extendData(data);

		return data;
	}

	@Override
	public Game initializeGame(final GameInitializationData data) {
		final Game game = new GameImp();

		dataExtender.extendData(game);
		effectProvider.getEffectCollection(game, null, GameInitializationPolicy.class).stream()
				.forEach(policy -> policy.initializeGame(game, data));

		return game;
	}
}
package com.pim.stars.game.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.game.api.GameInitializer;
import com.pim.stars.game.api.effects.GameInitializationPolicy;

public class GameInitializerImp implements GameInitializer {

	@Autowired(required = false)
	private final List<GameInitializationPolicy> gameInitializationPolicyList = new ArrayList<>();

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

		gameInitializationPolicyList.stream().forEach(policy -> policy.initializeGame(game, data));

		return game;
	}
}
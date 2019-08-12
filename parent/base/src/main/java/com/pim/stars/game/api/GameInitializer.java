package com.pim.stars.game.api;

public interface GameInitializer {

	public GameInitializationData createNewGameInitializationData();

	public Game initializeGame(GameInitializationData data);
}

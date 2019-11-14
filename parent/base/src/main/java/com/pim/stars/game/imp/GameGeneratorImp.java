package com.pim.stars.game.imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.effect.api.EffectProvider;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameGenerator;
import com.pim.stars.game.api.effects.GameGenerationPolicy;
import com.pim.stars.game.api.effects.GameGenerationPolicy.GameGenerationContext;
import com.pim.stars.game.imp.persistence.GamePersistenceInterface;

@Component
public class GameGeneratorImp implements GameGenerator {

	@Autowired
	private EffectProvider effectProvider;
	@Autowired
	private GamePersistenceInterface gamePersistenceInterface;

	@Override
	public Game generateGame(final Game game) {

		final Game newGame = new GameImp(game.getId(), game.getYear() + 1, (GameImp) game);
		doGameGeneration(newGame);
		return newGame;
	}

	private void doGameGeneration(final Game newGame) {
		final GameGenerationContext context = new GameGenerationContextImp();

		effectProvider.getEffectCollection(newGame, null, GameGenerationPolicy.class).stream()
				.forEach(policy -> policy.generateGame(newGame, context));

		gamePersistenceInterface.generateGame(newGame.getId(), newGame.getYear());
	}

	public class GameGenerationContextImp implements GameGenerationContext {

		private final Map<String, Object> data = new HashMap<>();

		@Override
		public Object get(final String key) {
			return data.get(key);
		}

		@Override
		public void set(final String key, final Object value) {
			data.put(key, value);
		}
	}
}

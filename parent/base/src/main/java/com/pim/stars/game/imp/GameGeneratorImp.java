package com.pim.stars.game.imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.effect.api.EffectExecutor;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameGenerator;
import com.pim.stars.game.api.effects.GameGenerationPolicy;
import com.pim.stars.game.api.effects.GameGenerationPolicy.GameGenerationContext;
import com.pim.stars.game.imp.persistence.GamePersistenceInterface;

@Component
public class GameGeneratorImp implements GameGenerator {

	@Autowired
	private EffectExecutor effectExecutor;
	@Autowired
	private GamePersistenceInterface gamePersistenceInterface;

	@Override
	public Game generateGame(final Game game) {

		final Game newGame = new GameImp(game.getId(), game.getYear() + 1);
		doGameGeneration(game, newGame);
		return newGame;
	}

	private void doGameGeneration(final Game previousYear, final Game currentYear) {
		final GameGenerationContext context = new GameGenerationContextImp(previousYear, currentYear);

		effectExecutor.executeEffect(currentYear, GameGenerationPolicy.class, null,
				(policy, effectContext) -> policy.generateGame(context));

		gamePersistenceInterface.generateGame(currentYear.getId(), currentYear.getYear());
	}

	public class GameGenerationContextImp implements GameGenerationContext {

		private final Map<String, Object> data = new HashMap<>();
		private final Game previousYear;
		private final Game currentYear;

		public GameGenerationContextImp(final Game previousYear, final Game currentYear) {
			this.previousYear = previousYear;
			this.currentYear = currentYear;
		}

		@Override
		public Object get(final String key) {
			return data.get(key);
		}

		@Override
		public void set(final String key, final Object value) {
			data.put(key, value);
		}

		@Override
		public Game getPreviousYear() {
			return previousYear;
		}

		@Override
		public Game getCurrentYear() {
			return currentYear;
		}
	}
}

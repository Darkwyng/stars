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

@Component
public class GameGeneratorImp implements GameGenerator {

	@Autowired
	private EffectProvider effectProvider;

	@Override
	public void generateGame(final Game game) {

		final GameGenerationContext context = new GameGenerationContextImp();

		effectProvider.getEffectCollection(game, null, GameGenerationPolicy.class).stream()
				.forEach(policy -> policy.generateGame(game, context));
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

package com.pim.stars.game.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameGenerator;
import com.pim.stars.game.api.effects.GameGenerationPolicy;

public class GameGeneratorImp implements GameGenerator {

	@Autowired(required = false)
	private final List<GameGenerationPolicy> gameGenerationPolicyList = new ArrayList<>();

	@Override
	public void generateGame(final Game game) {

		gameGenerationPolicyList.stream().forEach(policy -> policy.generateGame(game));
	}
}

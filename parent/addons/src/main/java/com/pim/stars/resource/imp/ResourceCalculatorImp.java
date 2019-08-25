package com.pim.stars.resource.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.resource.api.ResourceCalculator;
import com.pim.stars.resource.api.effects.ResourceCalculationPolicy;

@Component
public class ResourceCalculatorImp implements ResourceCalculator {

	@Autowired
	private EffectCalculator effectCalculator;

	@Override
	public int getResourcesForPlanet(final Game game, final Planet planet) {
		return effectCalculator.calculateEffect(game, ResourceCalculationPolicy.class, planet, 0,
				(policy, context, currentValue) -> policy.getPlanetResources(game, planet, currentValue));
	}

}

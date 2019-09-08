package com.pim.stars.production.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.effect.api.EffectCalculator;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.ProductionAvailabilityCalculator;
import com.pim.stars.production.api.effects.ProductionItemTypeAvailabilityPolicy;
import com.pim.stars.production.api.policies.ProductionItemType;

@Component
public class ProductionAvailabilityCalculatorImp implements ProductionAvailabilityCalculator {

	@Autowired
	private EffectCalculator effectCalculator;

	@Override
	public boolean isProductionItemTypeAvailable(final Game game, final Planet planet,
			final ProductionItemType itemType) {
		return effectCalculator.calculateEffect(game, ProductionItemTypeAvailabilityPolicy.class, planet, false,
				(policy, context, currentValue) -> isProductionItemTypeAvailable(game, planet, itemType, policy,
						currentValue));
	}

	private Boolean isProductionItemTypeAvailable(final Game game, final Planet planet,
			final ProductionItemType itemType, final ProductionItemTypeAvailabilityPolicy policy,
			final Boolean currentValue) {
		final boolean isRelevantPolicy = policy.getProductionItemTypeClass().isAssignableFrom(itemType.getClass());
		if (isRelevantPolicy) {
			return policy.isProductionItemTypeAvailable(game, planet, currentValue);
		} else {
			return currentValue;
		}
	}
}

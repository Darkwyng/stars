package com.pim.stars.mineral.imp.effects;

import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.mineral.imp.policies.MineProductionItemType;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.effects.ProductionItemTypeAvailabilityPolicy;
import com.pim.stars.production.api.policies.ProductionItemType;

@Component
public class DefaultMineProductionItemTypeAvailabilityPolicy implements ProductionItemTypeAvailabilityPolicy {

	@Override
	public Class<? extends ProductionItemType> getProductionItemTypeClass() {
		return MineProductionItemType.class;
	}

	@Override
	public boolean isProductionItemTypeAvailable(final Game game, final Planet planet, final boolean isAvailable) {
		return true;
	}
}

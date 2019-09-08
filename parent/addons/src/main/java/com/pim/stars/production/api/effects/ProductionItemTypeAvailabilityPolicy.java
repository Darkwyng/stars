package com.pim.stars.production.api.effects;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.production.api.policies.ProductionItemType;

public interface ProductionItemTypeAvailabilityPolicy extends Effect {

	public Class<? extends ProductionItemType> getProductionItemTypeClass();

	public boolean isProductionItemTypeAvailable(Game game, Planet planet, boolean isAvailable);
}

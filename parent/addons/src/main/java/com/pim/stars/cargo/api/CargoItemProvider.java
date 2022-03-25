package com.pim.stars.cargo.api;

import java.util.Map;

import com.pim.stars.cargo.api.policies.CargoHolderDefinition;
import com.pim.stars.game.api.Game;

public interface CargoItemProvider {

	public Map<String, CargoHolder> getItemsForCargoHolderType(final Game game,
			final CargoHolderDefinition<? extends Object> cargoHolderDefinition);
}

package com.pim.stars.cargo.api;

import java.util.Collection;

import com.pim.stars.game.api.Game;

public interface CargoProcessor {

	public CargoHolder createCargoHolder(Game game, Object entity);

	public CargoHolder createCargoHolder();

	public CargoHolder add(Collection<CargoHolder> cargoHolders);
}

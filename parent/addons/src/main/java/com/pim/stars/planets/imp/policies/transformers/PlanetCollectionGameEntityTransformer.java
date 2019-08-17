package com.pim.stars.planets.imp.policies.transformers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.game.api.Game;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.GamePlanetCollection;
import com.pim.stars.turn.api.utilities.CollectionGameEntityTransformer;

public class PlanetCollectionGameEntityTransformer extends CollectionGameEntityTransformer<Game, Planet> {

	@Autowired
	private GamePlanetCollection dataExtensionPolicy;

	@Override
	public DataExtensionPolicy<Game, Collection<Planet>> getExtensionToTransform() {
		return dataExtensionPolicy;
	}
}
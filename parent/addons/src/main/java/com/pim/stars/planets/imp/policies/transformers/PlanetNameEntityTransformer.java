package com.pim.stars.planets.imp.policies.transformers;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.extensions.PlanetName;
import com.pim.stars.turn.api.utilities.CopyGameEntityTransformer;

public class PlanetNameEntityTransformer extends CopyGameEntityTransformer<Planet, String> {

	@Autowired
	private PlanetName dataExtensionPolicy;

	@Override
	public DataExtensionPolicy<Planet, String> getExtensionToTransform() {
		return dataExtensionPolicy;
	}
}
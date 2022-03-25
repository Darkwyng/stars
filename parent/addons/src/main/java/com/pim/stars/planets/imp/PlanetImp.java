package com.pim.stars.planets.imp;

import java.util.Optional;

import com.pim.stars.planets.api.Planet;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class PlanetImp implements Planet {

	private final String name;
	private final String ownerId;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Optional<String> getOwnerId() {
		return Optional.ofNullable(ownerId);
	}
}

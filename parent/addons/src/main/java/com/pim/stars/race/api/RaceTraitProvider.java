package com.pim.stars.race.api;

import java.util.Collection;
import java.util.Optional;

import com.pim.stars.race.api.traits.PrimaryRacialTrait;
import com.pim.stars.race.api.traits.SecondaryRacialTrait;

public interface RaceTraitProvider {

	public Collection<PrimaryRacialTrait> getPrimaryRacialTraitCollection();

	public Collection<SecondaryRacialTrait> getSecondaryRacialTraitCollection();

	public Optional<PrimaryRacialTrait> getPrimaryRacialTraitById(String id);

	public Optional<SecondaryRacialTrait> getSecondaryRacialTraitById(String id);
}
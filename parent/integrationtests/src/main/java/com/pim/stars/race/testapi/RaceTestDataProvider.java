package com.pim.stars.race.testapi;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.race.api.RaceInitializationData;
import com.pim.stars.race.api.RaceInitializer;
import com.pim.stars.race.api.RaceTraitProvider;
import com.pim.stars.race.api.extensions.RacePrimaryRacialTrait;
import com.pim.stars.race.api.extensions.RaceSecondaryRacialTraitCollection;
import com.pim.stars.race.api.traits.PrimaryRacialTrait;
import com.pim.stars.race.api.traits.SecondaryRacialTrait;

public class RaceTestDataProvider {

	@Autowired
	private RaceInitializer raceInitializer;
	@Autowired
	private RaceTraitProvider raceTraitProvider;
	@Autowired
	private RacePrimaryRacialTrait racePrimaryRacialTrait;
	@Autowired
	private RaceSecondaryRacialTraitCollection raceSecondaryRacialTraitCollection;

	public RaceInitializationData createRace() {
		return createRace(null);
	}

	public RaceInitializationData createRace(final String primaryTraitId, final String... secondaryTraitIds) {
		final RaceInitializationData newRace = raceInitializer.initializeRace();
		racePrimaryRacialTrait.setValue(newRace, getPrimaryTrait(primaryTraitId));
		addSecondaryTraits(newRace, secondaryTraitIds);

		return newRace;
	}

	private PrimaryRacialTrait getPrimaryTrait(final String primaryTraitId) {
		final PrimaryRacialTrait primaryRacialTrait;
		if (primaryTraitId == null) {
			primaryRacialTrait = raceTraitProvider.getPrimaryRacialTraitCollection().stream()
					.sorted(Comparator.comparing(PrimaryRacialTrait::getId)).findFirst().get();
		} else {
			final Optional<PrimaryRacialTrait> optional = raceTraitProvider.getPrimaryRacialTraitById(primaryTraitId);
			assertThat(
					"No trait was found for ID " + primaryTraitId + ". Did you mean one of these? "
							+ raceTraitProvider.getPrimaryRacialTraitCollection().stream()
									.map(PrimaryRacialTrait::getId).sorted().collect(Collectors.joining(", ")),
					optional.isPresent(), is(true));
			primaryRacialTrait = optional.get();
		}
		return primaryRacialTrait;
	}

	private void addSecondaryTraits(final RaceInitializationData newRace, final String... secondaryTraitIds) {
		final Collection<SecondaryRacialTrait> secondaryTraits = raceSecondaryRacialTraitCollection.getValue(newRace);
		Arrays.stream(secondaryTraitIds).map(traitId -> raceTraitProvider.getSecondaryRacialTraitById(traitId))
				.peek(optional -> assertThat(
						"No trait was found. Did you mean one of these? "
								+ raceTraitProvider.getSecondaryRacialTraitCollection().stream()
										.map(SecondaryRacialTrait::getId).sorted().collect(Collectors.joining(", ")),
						optional.isPresent(), is(true)))
				.map(Optional::get).forEach(secondaryTraits::add);
	}
}
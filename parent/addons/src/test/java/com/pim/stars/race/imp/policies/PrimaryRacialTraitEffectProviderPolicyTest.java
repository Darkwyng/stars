package com.pim.stars.race.imp.policies;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.mockito.Mockito.mock;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.effect.api.Effect;
import com.pim.stars.race.RaceTestConfiguration;
import com.pim.stars.race.api.RaceInitializer;
import com.pim.stars.race.api.RaceTraitProvider;
import com.pim.stars.race.api.extensions.RacePrimaryRacialTrait;
import com.pim.stars.race.api.traits.PrimaryRacialTrait;
import com.pim.stars.turn.api.Race;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RaceTestConfiguration.class)
public class PrimaryRacialTraitEffectProviderPolicyTest {

	@Autowired
	private PrimaryRacialTraitEffectProviderPolicy testee;
	@Autowired
	private RacePrimaryRacialTrait racePrimaryRacialTrait;
	@Autowired
	private RaceInitializer raceInitializer;
	@Autowired
	private RaceTraitProvider raceTraitProvider;

	@Test
	public void testThatEffectHolderIsMatched() {
		assertThat(testee.matchesEffectHolder(mock(Race.class)), is(true));
		assertThat(testee.matchesEffectHolder(new Object()), is(false));
	}

	@Test
	public void testThatEffectsOfTraitAreReturned() {
		final Race race = raceInitializer.initializeRace();
		final PrimaryRacialTrait trait = raceTraitProvider.getPrimaryRacialTraitById("HyperExpander").get();
		racePrimaryRacialTrait.setValue(race, trait);
		assertThat(trait.getEffectCollection(), not(empty()));
		final Class<? extends Effect> effectClass = trait.getEffectCollection().iterator().next().getClass();

		final Collection<? extends Effect> effectCollection = testee.getEffectCollectionFromEffectHolder(race,
				effectClass);
		assertThat(effectCollection, not(empty()));
	}
}
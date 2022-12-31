package com.pim.stars.design.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pim.stars.design.api.Design;
import com.pim.stars.design.api.DesignDefiner.DesignBuilder;
import com.pim.stars.design.imp.persistence.DesignRepository;
import com.pim.stars.gadget.api.hull.Hull;
import com.pim.stars.gadget.api.types.HullType;
import com.pim.stars.gadget.imp.gadget.GadgetImp;
import com.pim.stars.gadget.imp.hull.GadgetSlotImp;
import com.pim.stars.gadget.imp.hull.HullImp;
import com.pim.stars.gadget.imp.types.GadgetTypeImp;
import com.pim.stars.game.api.Game;
import com.pim.stars.id.api.IdCreator;
import com.pim.stars.race.api.Race;

class DesignDefinerImpTest {

	@Mock
	private IdCreator idCreator;
	@Mock
	private DesignRepository designRepository;
	@InjectMocks
	private DesignDefinerImp testee;

	private Hull hull;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		when(idCreator.createId()).thenReturn("myId");
		hull = new HullImp("myId", Collections.emptyList(), mock(HullType.class),
				Arrays.asList(
						new GadgetSlotImp("1", 0, 3,
								Arrays.asList(new GadgetTypeImp("Armor"), new GadgetTypeImp("Shield"))),
						new GadgetSlotImp("2", 2, 2, Arrays.asList(new GadgetTypeImp("Engine")))));
	}

	@Test
	void testThatAllSlotsCanBeFilled() {
		final Design design = testee.start(mockGame("17", 2405), mockRace("myRace"), hull)
				.fillSlot("1", new GadgetImp("myArmor", Collections.emptyList(), new GadgetTypeImp("Armor")), 3)
				.fillSlot("2", new GadgetImp("myEngine", Collections.emptyList(), new GadgetTypeImp("Engine")), 2)
				.build("myName");
		assertThat(design.getName(), is("myName"));
		assertThat(design.getId(), is("myId"));
		assertThat(design.getOwnerId(), is("myRace"));
		verify(idCreator, times(1)).createId();
		verify(designRepository).save(notNull());
	}

	@Test
	void testThatOnlyEngineSlotCanBeFilled() {
		testee.start(mockGame("17", 2405), mockRace("myRace"), hull)
				.fillSlot("2", new GadgetImp("myEngine", Collections.emptyList(), new GadgetTypeImp("Engine")), 2)
				.build("myName");
	}

	@ParameterizedTest
	@ValueSource(ints = { -1, 0, 1, 3 })
	void testThatOutOfBoundsNumberIsRejected(final int number) {
		assertThrows(Exception.class, () -> testee.start(mockGame("17", 2405), mockRace("myRace"), hull).fillSlot("2",
				new GadgetImp("myEngine", Collections.emptyList(), new GadgetTypeImp("Engine")), number));
	}

	@Test
	void testThatWrongSlotIdIsRejected() {
		assertThrows(Exception.class,
				() -> testee.start(mockGame("17", 2405), mockRace("myRace"), hull).fillSlot("wrongId",
						new GadgetImp("myEngine", Collections.emptyList(), new GadgetTypeImp("Engine")), 2));
	}

	@Test
	void testThatWrongGadgetTypeIsRejected() {
		assertThrows(Exception.class, () -> testee.start(mockGame("17", 2405), mockRace("myRace"), hull).fillSlot("2",
				new GadgetImp("myEngine", Collections.emptyList(), new GadgetTypeImp("NotAnEngine")), 2));
	}

	@Test
	void testThatFillingSlotTwiceIsRejected() {
		final DesignBuilder builder = testee.start(mockGame("17", 2405), mockRace("myRace"), hull).fillSlot("2",
				new GadgetImp("myEngine", Collections.emptyList(), new GadgetTypeImp("Engine")), 2);
		assertThrows(Exception.class, () -> builder.fillSlot("2",
				new GadgetImp("myEngine", Collections.emptyList(), new GadgetTypeImp("Engine")), 2));
	}

	private Game mockGame(final String gameId, final int year) {
		final Game game = mock(Game.class);
		when(game.getId()).thenReturn(gameId);
		when(game.getYear()).thenReturn(year);
		return game;
	}

	private Race mockRace(final String raceId) {
		final Race race = mock(Race.class);
		when(race.getId()).thenReturn(raceId);
		return race;
	}
}

package com.pim.stars.gadget.imp;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Collection;
import java.util.List;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.gadget.GadgetTestConfiguration;
import com.pim.stars.gadget.api.Gadget;
import com.pim.stars.gadget.api.GadgetProvider;
import com.pim.stars.gadget.api.hull.GadgetSlot;
import com.pim.stars.gadget.api.hull.Hull;
import com.pim.stars.gadget.api.types.GadgetType;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { GadgetTestConfiguration.WithoutPersistence.class })
@ActiveProfiles("WithoutPersistence")
public class GadgetProviderImpComponentTest {

	@Autowired
	private GadgetProvider testee;

	@Test
	public void testThatGadgetsAreLoadedFromXml() {
		final List<Gadget> allGadgets = testee.getGadgets().collect(toList());
		assertThat(allGadgets, not(emptyIterable()));

		final Gadget ferretScanner = testee.getGadgets().filter(gadget -> gadget.getId().equals("BatScanner")).findAny()
				.get();
		assertThat(ferretScanner.getEffectCollection(), not(emptyIterable()));
		assertThat(ferretScanner.getGadgetType(), not(nullValue()));
		assertThat(ferretScanner.getGadgetType().getId(), is("Scanner"));

		final List<Gadget> allScanners = testee.getGadgetsByType(ferretScanner.getGadgetType()).collect(toList());
		assertThat(allScanners, hasItems(withId("BatScanner"), withId("RhinoScanner")));

		final Gadget engine = testee.getGadgetById("SettlersDelight");
		assertThat(engine.getGadgetType().getId(), is("Engine"));

		assertThrows(IllegalArgumentException.class, () -> testee.getGadgetById("Whrgl"));
	}

	@Test
	public void testThatGadgetTypesAreLoadedFromXml() {
		Collection<GadgetType> gadgetTypes = testee.getGadgetTypes();
		assertThat(gadgetTypes, hasItems(withId("Engine"), withId("Armor")));

		gadgetTypes = testee.getGadgetTypes(); // test lazy loading
		assertThat(gadgetTypes, hasItems(withId("Scanner"), withId("Shield")));

		assertThrows(IllegalArgumentException.class, () -> testee.getGadgetTypeById("Whrgl"));
	}

	@Test
	public void testThatGadgetTypesAreLoadedFromXmlById() {
		GadgetType gadgetType = testee.getGadgetTypeById("Scanner");
		assertThat(gadgetType.getId(), is("Scanner"));

		gadgetType = testee.getGadgetTypeById("Armor"); // test lazy loading
		assertThat(gadgetType.getId(), is("Armor"));
	}

	@Test
	public void testThatHullsAreLoadedFromXml() {
		final List<Hull> allHulls = testee.getHulls().collect(toList());
		assertThat(allHulls, not(emptyIterable()));

		final Hull colonizer = testee.getHulls().filter(gadget -> gadget.getId().equals("Colonizer")).findAny().get();
		assertThat(colonizer.getEffectCollection(), not(emptyIterable()));
		assertThat(colonizer.getHullType(), not(nullValue()));
		assertThat(colonizer.getHullType().getId(), is("Ship"));

		final List<Hull> allShips = testee.getHullsByType(colonizer.getHullType()).collect(toList());
		assertThat(allShips, hasItems(withId("Colonizer"), withId("Frigate")));

		final Hull frigate = testee.getHullById("Frigate");
		assertThat(frigate.getHullType().getId(), is("Ship"));

		assertThrows(IllegalArgumentException.class, () -> testee.getHullById("Whrgl"));

		// Check slots:
		assertThat(frigate.getGadgetSlots(), hasItems(withSlotId("1"), withSlotId("2"), withSlotId("3")));
		for (final GadgetSlot slot : frigate.getGadgetSlots()) {
			switch (slot.getSlotId()) {
			case "1":
				assertThat(slot.getAllowedGadgetTypes(), containsInAnyOrder(withId("Engine")));
				assertThat(slot.getMinimumNumberOfGadgets(), is(1));
				assertThat(slot.getMaximumNumberOfGadgets(), is(1));
				break;
			case "2":
				assertThat(slot.getAllowedGadgetTypes(), containsInAnyOrder(withId("Shield"), withId("Armor")));
				assertThat(slot.getMinimumNumberOfGadgets(), is(0));
				assertThat(slot.getMaximumNumberOfGadgets(), is(2));
				break;
			case "3":
				assertThat(slot.getAllowedGadgetTypes(),
						hasItems(withId("Shield"), withId("Armor"), withId("Scanner"), withId("Mechanical")));
				assertThat(slot.getAllowedGadgetTypes(), not(hasItems(withId("Engine"))));
				assertThat(slot.getMinimumNumberOfGadgets(), is(0));
				assertThat(slot.getMaximumNumberOfGadgets(), is(3));
				break;
			case "4":
				assertThat(slot.getAllowedGadgetTypes(), containsInAnyOrder(withId("Scanner")));
				assertThat(slot.getMinimumNumberOfGadgets(), is(0));
				assertThat(slot.getMaximumNumberOfGadgets(), is(2));
				break;
			default:
				fail("Unexpected slot ID: " + slot.getSlotId());
			}
		}
	}

	private Matcher<Object> withId(final String id) {
		return hasProperty("id", is(id));
	}

	private Matcher<Object> withSlotId(final String id) {
		return hasProperty("slotId", is(id));
	}
}
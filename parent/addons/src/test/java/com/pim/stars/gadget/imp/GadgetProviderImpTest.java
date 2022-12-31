package com.pim.stars.gadget.imp;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.pim.stars.gadget.api.types.GadgetType;
import com.pim.stars.gadget.imp.hull.input.GadgetSlotFromXml;
import com.pim.stars.gadget.imp.types.GadgetTypeImp;

public class GadgetProviderImpTest {

	@Spy
	private GadgetProperties gadgetProperties;

	@InjectMocks
	private GadgetProviderImp testee;

	@BeforeEach
	private void setUp() {
		MockitoAnnotations.initMocks(this);
		testee.gadgetTypeByIdMap.put("Engine", new GadgetTypeImp("Engine"));
		testee.gadgetTypeByIdMap.put("Scanner", new GadgetTypeImp("Scanner"));
		testee.gadgetTypeByIdMap.put("Armor", new GadgetTypeImp("Armor"));
	}

	@Test
	public void testAnyGadgetTypeDoesNotIncludeEngines() {
		final GadgetSlotFromXml slot = new GadgetSlotFromXml();
		slot.setAllowedGadgetTypeIds("Any");

		final Collection<GadgetType> types = testee.getGadgetTypesOfSlot("hullId", slot);
		assertThat(types, hasItems(withId("Scanner"), withId("Armor")));
		assertThat(types, not(hasItems(withId("Engine"))));

		testee.getGadgetTypesOfSlot("hullId", slot); // test lazy loading
	}

	@Test
	public void testUnknownGadgetTypesAreRejected() {
		final GadgetSlotFromXml slot = new GadgetSlotFromXml();
		slot.setAllowedGadgetTypeIds("Armor, Whrgl, Scanner, Blubb");

		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> testee.getGadgetTypesOfSlot("hullId", slot));
		assertThat(exception.getMessage(),
				allOf(containsString("Whrgl, Blubb"), containsString(slot.getAllowedGadgetTypeIds())));
	}

	@Test
	public void testNullGadgetTypesAreRejected() {
		testEmptyGadgetTypesAreRejected(null);
	}

	@ParameterizedTest()
	@ValueSource(strings = { "", " ", "   " })
	public void testEmptyGadgetTypesAreRejected(final String allowedGadgetTypeIds) {
		final GadgetSlotFromXml slot = new GadgetSlotFromXml();
		slot.setAllowedGadgetTypeIds(allowedGadgetTypeIds);

		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> testee.getGadgetTypesOfSlot("hullId", slot));
		assertThat(exception.getMessage(),
				allOf(containsString("is empty"), containsString(String.valueOf(slot.getAllowedGadgetTypeIds()))));
	}

	@ParameterizedTest()
	@ValueSource(strings = { "", " ", "Hello World", "1-2-3", "1-" })
	public void testWrongBoundariesAreRejected(final String allowedNumberRange) {
		final GadgetSlotFromXml slot = new GadgetSlotFromXml();
		slot.setAllowedNumberRange(allowedNumberRange);

		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> testee.getBoundariesOfSlot("hullId", slot));
		assertThat(exception.getMessage(),
				allOf(containsString("exactly two"), containsString(slot.getAllowedNumberRange())));
	}

	@ParameterizedTest()
	@ValueSource(strings = { "1-a", "2-b", "12.1-13", "14-15,1", "1- ", " -3", "-2" })
	public void testThatNumberFormatExceptionIsHandled(final String allowedNumberRange) {
		final GadgetSlotFromXml slot = new GadgetSlotFromXml();
		slot.setAllowedNumberRange(allowedNumberRange);

		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> testee.getBoundariesOfSlot("hullId", slot));
		assertThat(exception.getMessage(),
				allOf(containsString("formatted"), containsString(slot.getAllowedNumberRange())));
	}

	@ParameterizedTest()
	@ValueSource(strings = { "1-0", "17-1", "102-99" })
	public void testThatIncorrectOrderIsRejected(final String allowedNumberRange) {
		final GadgetSlotFromXml slot = new GadgetSlotFromXml();
		slot.setAllowedNumberRange(allowedNumberRange);

		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> testee.getBoundariesOfSlot("hullId", slot));
		assertThat(exception.getMessage(),
				allOf(containsString("is larger"), containsString(slot.getAllowedNumberRange())));
	}

	private Matcher<Object> withId(final String id) {
		return hasProperty("id", is(id));
	}
}

package com.pim.stars.gadget.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.pim.stars.gadget.api.Gadget;
import com.pim.stars.gadget.api.GadgetProvider;
import com.pim.stars.gadget.api.hull.GadgetSlot;
import com.pim.stars.gadget.api.hull.Hull;
import com.pim.stars.gadget.api.types.GadgetType;
import com.pim.stars.gadget.api.types.HullType;
import com.pim.stars.gadget.imp.gadget.GadgetImp;
import com.pim.stars.gadget.imp.gadget.input.GadgetFromXml;
import com.pim.stars.gadget.imp.hull.GadgetSlotImp;
import com.pim.stars.gadget.imp.hull.HullImp;
import com.pim.stars.gadget.imp.hull.input.GadgetSlotFromXml;
import com.pim.stars.gadget.imp.hull.input.HullFromXml;
import com.pim.stars.gadget.imp.types.GadgetTypeImp;
import com.pim.stars.gadget.imp.types.HullTypeImp;
import com.pim.stars.spring.api.BeanLoader;

@Component
public class GadgetProviderImp implements GadgetProvider {

	private static final String MARKER_FOR_VERY_FLEXIBLE_SLOT = "Any";

	private Collection<Gadget> gadgets = null;
	private Collection<Hull> hulls = null;

	protected final Map<String, GadgetType> gadgetTypeByIdMap = new HashMap<>();
	private final Map<String, HullType> hullTypeByIdMap = new HashMap<>();
	private Collection<GadgetType> gadgetTypesForVeryFlexibleSlot;

	@Autowired
	private BeanLoader beanLoader;
	@Autowired
	private GadgetProperties gadgetProperties;

	@Override
	public Collection<GadgetType> getGadgetTypes() {
		ensureLoadedGadgetTypes();
		return gadgetTypeByIdMap.values();
	}

	@Override
	public GadgetType getGadgetTypeById(final String id) {
		ensureLoadedGadgetTypes();

		final GadgetType gadgetType = gadgetTypeByIdMap.get(id);
		if (gadgetType != null) {
			return gadgetType;
		} else {
			throw new IllegalArgumentException("No entry found for id " + id);
		}
	}

	@Override
	public Stream<Gadget> getGadgets() {
		if (gadgets == null) {
			gadgets = loadGadgetsFromXml();
		}
		return gadgets.stream();
	}

	@Override
	public Stream<Hull> getHulls() {
		if (hulls == null) {
			ensureLoadedGadgetTypes();
			hulls = loadHullsFromXml();
		}
		return hulls.stream();
	}

	@Override
	public Stream<Gadget> getGadgetsByType(final GadgetType type) {
		return getGadgets().filter(entry -> type.equals(entry.getGadgetType()));
	}

	@Override
	public Stream<Hull> getHullsByType(final HullType type) {
		return getHulls().filter(entry -> type.equals(entry.getHullType()));
	}

	@Override
	public Gadget getGadgetById(final String id) {
		return getGadgets().filter(entry -> entry.getId().equals(id)).findAny()
				.orElseThrow(() -> new IllegalArgumentException("No entry found for id " + id));
	}

	@Override
	public Hull getHullById(final String id) {
		return getHulls().filter(entry -> entry.getId().equals(id)).findAny()
				.orElseThrow(() -> new IllegalArgumentException("No entry found for id " + id));
	}

	private void ensureLoadedGadgetTypes() {
		if (gadgets == null) {
			getGadgets();
		}
	}

	private Collection<Gadget> loadGadgetsFromXml() {

		return beanLoader.loadFromXml(GadgetFromXml.class, gadgetProperties.getGadgetFilePaths(), (id, data) -> {
			final GadgetType gadgetType = gadgetTypeByIdMap.computeIfAbsent(data.getGadgetTypeId(), GadgetTypeImp::new);
			return new GadgetImp(id, data.getEffectCollection(), gadgetType);
		}, data -> data.getEffectCollection().stream());
	}

	private Collection<Hull> loadHullsFromXml() {

		return beanLoader.loadFromXml(HullFromXml.class, gadgetProperties.getHullFilePaths(), (id, data) -> {
			final HullType hullType = hullTypeByIdMap.computeIfAbsent(data.getHullTypeId(), HullTypeImp::new);
			return new HullImp(id, data.getEffectCollection(), hullType, getGadgetSlots(id, data));
		}, data -> data.getEffectCollection().stream());
	}

	private Collection<GadgetSlot> getGadgetSlots(final String hullId, final HullFromXml data) {
		return data.getGadgetSlots().stream().map(slot -> {
			final Collection<GadgetType> types = getGadgetTypesOfSlot(hullId, slot);
			final int[] boundaries = getBoundariesOfSlot(hullId, slot);
			return new GadgetSlotImp(slot.getId(), boundaries[0], boundaries[1], types);
		}).collect(Collectors.toList());
	}

	protected Collection<GadgetType> getGadgetTypesOfSlot(final String hullId, final GadgetSlotFromXml slot) {
		final String allowedGadgetTypeIds = slot.getAllowedGadgetTypeIds();
		if (allowedGadgetTypeIds != null && Strings.isNullOrEmpty(allowedGadgetTypeIds.trim())) {
			throw gadgetTypeParsingError(hullId, slot, "The list of types is empty.");
		} else if (MARKER_FOR_VERY_FLEXIBLE_SLOT.equals(allowedGadgetTypeIds)) {
			return getGadgetTypesForVeryFlexibleSlot();
		} else {
			final Collection<String> unknownTypeIds = new ArrayList<>();
			final Collection<GadgetType> result = new ArrayList<>();

			for (final String typeId : allowedGadgetTypeIds.split(",")) {
				final GadgetType type = gadgetTypeByIdMap.get(typeId.trim());
				if (type == null) {
					unknownTypeIds.add(typeId.trim());
				} else {
					result.add(type);
				}
			}
			if (unknownTypeIds.isEmpty()) {
				return Collections.unmodifiableCollection(result);
			} else {
				throw gadgetTypeParsingError(hullId, slot, "There are no gadget types with these IDs: "
						+ unknownTypeIds.stream().collect(Collectors.joining(", ")));
			}
		}
	}

	private Collection<GadgetType> getGadgetTypesForVeryFlexibleSlot() {
		if (gadgetTypesForVeryFlexibleSlot == null) {
			gadgetTypesForVeryFlexibleSlot = gadgetTypeByIdMap.values().stream()
					.filter(type -> !type.getId().equals("Engine")).collect(Collectors.toUnmodifiableList());
		}

		return gadgetTypesForVeryFlexibleSlot;
	}

	private IllegalArgumentException gadgetTypeParsingError(final String hullId, final GadgetSlotFromXml slot,
			final String errorMessage) {
		return new IllegalArgumentException("The alllowedGadgetTypeIds '" + slot.getAllowedGadgetTypeIds()
				+ "' of slot '" + slot.getId() + "' of hull '" + hullId + "' could not be parsed."
				+ " It is expected to be either '" + MARKER_FOR_VERY_FLEXIBLE_SLOT
				+ "' or a comma separated list of IDs of gadget types. " + errorMessage);
	}

	protected int[] getBoundariesOfSlot(final String hullId, final GadgetSlotFromXml slot) {
		final String[] boundaries = slot.getAllowedNumberRange().split("\\-");
		if (boundaries.length != 2) {
			throw boundaryParsingError(hullId, slot.getId(), slot.getAllowedNumberRange(),
					boundaries.length + " values were found, exactly two are expected.");
		} else {
			int min;
			int max;
			try {
				min = Integer.parseInt(boundaries[0]);
				max = Integer.parseInt(boundaries[1]);
			} catch (final NumberFormatException e) {
				throw boundaryParsingError(hullId, slot.getId(), slot.getAllowedNumberRange(),
						"The numbers must be formatted as integers.");
			}
			if (min > max) {
				throw boundaryParsingError(hullId, slot.getId(), slot.getAllowedNumberRange(),
						min + " is larger than " + max);
			}
			return new int[] { min, max };
		}
	}

	private IllegalArgumentException boundaryParsingError(final String hullId, final String slotId,
			final String allowedNumberRange, final String errorMessage) {
		return new IllegalArgumentException("The allowedNumberRange '" + allowedNumberRange + "' of slot '" + slotId
				+ "' of hull '" + hullId + "' could not be parsed."
				+ " It is expected to be a range of two integers, separated by '-', where the first is not larger than the second: "
				+ errorMessage);
	}
}
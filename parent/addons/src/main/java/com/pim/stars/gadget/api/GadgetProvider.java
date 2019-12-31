package com.pim.stars.gadget.api;

import java.util.Collection;
import java.util.stream.Stream;

import com.pim.stars.gadget.api.hull.Hull;
import com.pim.stars.gadget.api.types.GadgetType;
import com.pim.stars.gadget.api.types.HullType;

public interface GadgetProvider {

	public Collection<GadgetType> getGadgetTypes();

	public GadgetType getGadgetTypeById(String id);

	public Stream<Gadget> getGadgets();

	public Stream<Hull> getHulls();

	public Stream<Gadget> getGadgetsByType(GadgetType type);

	public Stream<Hull> getHullsByType(HullType type);

	public Gadget getGadgetById(String id);

	public Hull getHullById(String id);
}

package com.pim.stars.production.imp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductionQueue implements Iterable<ProductionQueueEntry> {

	private final List<ProductionQueueEntry> entries = new ArrayList<>();
	private final String planetName;

	public ProductionQueue(final String planetName) {
		super();
		this.planetName = planetName;
	}

	public String getPlanetName() {
		return planetName;
	}

	@Override
	public Iterator<ProductionQueueEntry> iterator() {
		return entries.iterator();
	}

	public void addEntry(final ProductionQueueEntry entry) {
		entries.add(entry);
	}

	public void cleanUp() {
		final Collection<ProductionQueueEntry> toBeRemoved = entries.stream()
				.filter(entry -> entry.getNumberOfItemsToBuild() == 0).collect(Collectors.toList());
		entries.removeAll(toBeRemoved);
	}

	public boolean isEmpty() {
		return entries.isEmpty();
	}

	@Override
	public String toString() {
		return planetName + " [" + entries.stream().map(Object::toString).collect(Collectors.joining(",")) + "]";
	}
}
package com.pim.stars.production.imp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProductionQueue implements Iterable<ProductionQueueEntry> {

	private final List<ProductionQueueEntry> entries = new ArrayList<>();

	@Override
	public Iterator<ProductionQueueEntry> iterator() {
		return entries.iterator();
	}

	public void addEntry(final ProductionQueueEntry entry) {
		entries.add(entry);
	}

	public boolean isEmpty() {
		return entries.isEmpty();
	}
}

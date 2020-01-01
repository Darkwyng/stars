package com.pim.stars.fleet.api;

import java.util.Collection;

public interface Fleet {

	public String getId();

	public String getName();

	public String getOwnerId();

	public Collection<FleetComponent> getComponents();

	public interface FleetComponent {

		public String getShipDesignId();

		public int getNumberOfShips();
	}
}
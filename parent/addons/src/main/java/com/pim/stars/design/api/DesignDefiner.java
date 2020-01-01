package com.pim.stars.design.api;

import com.pim.stars.gadget.api.Gadget;
import com.pim.stars.gadget.api.hull.GadgetSlot;
import com.pim.stars.gadget.api.hull.Hull;
import com.pim.stars.game.api.Game;
import com.pim.stars.race.api.Race;

public interface DesignDefiner {

	public DesignBuilder start(Game game, Race race, Hull hull);

	public DesignBuilder start(Game game, String raceId, String hullId);

	public interface DesignBuilder {

		public DesignBuilder fillSlot(GadgetSlot slot, Gadget gadget, int numberOfGadgets);

		public DesignBuilder fillSlot(String slotId, String gadgetId, int numberOfGadgets);

		public Design build(String designName);
	}
}

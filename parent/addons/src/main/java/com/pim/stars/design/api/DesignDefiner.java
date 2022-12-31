package com.pim.stars.design.api;

import com.pim.stars.gadget.api.Gadget;
import com.pim.stars.gadget.api.hull.Hull;
import com.pim.stars.game.api.Game;
import com.pim.stars.race.api.Race;

public interface DesignDefiner {

	public DesignBuilder start(Game game, Race race, Hull hull);

	public interface DesignBuilder {

		public DesignBuilder fillSlot(String slotId, Gadget gadget, int numberOfGadgets);

		public Design build(String designName);
	}
}

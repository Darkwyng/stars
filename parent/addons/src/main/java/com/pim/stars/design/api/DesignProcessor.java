package com.pim.stars.design.api;

import com.pim.stars.game.api.Game;

public interface DesignProcessor {

	public void activateDesign(Game game, String designId);

	public void deleteDesign(Game game, String designId);
}

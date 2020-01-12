package com.pim.stars.location.api;

import com.pim.stars.game.api.Game;

public interface UniverseSizeProvider {

	public UniverseSize getUniverseSize(Game game);

	public interface UniverseSize {

		public int getMinX();

		public int getMinY();

		public int getMaxX();

		public int getMaxY();
	}
}
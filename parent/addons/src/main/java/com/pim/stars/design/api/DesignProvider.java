package com.pim.stars.design.api;

import java.util.stream.Stream;

import com.pim.stars.game.api.Game;
import com.pim.stars.race.api.Race;

public interface DesignProvider {

	public Design getDesignById(Game game, String designId);

	public Stream<Design> getAllDesignsByOwner(Game game, Race race);

	public Stream<Design> getEditableDesignsByOwner(Game game, Race race);

	public Stream<Design> getActiveDesignsByOwner(Game game, Race race);
}
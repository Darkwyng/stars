package com.pim.stars.game.imp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(GameProperties.class)
@ConfigurationProperties(prefix = "game")
public class GameProperties {

	private int startingYear = 2500;

	public int getStartingYear() {
		return startingYear;
	}

	public void setStartingYear(final int startingYear) {
		this.startingYear = startingYear;
	}
}

package com.pim.stars.report.api;

import java.util.Locale;
import java.util.stream.Stream;

import com.pim.stars.game.api.Game;
import com.pim.stars.race.api.Race;

public interface ReportProvider {

	public Stream<MessageReport> getReports(Game game, Race race, Locale locale);

	public interface MessageReport {

		public String getType();

		public String getMessage();
	}
}
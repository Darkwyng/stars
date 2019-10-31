package com.pim.stars.report.api;

import com.pim.stars.game.api.Game;
import com.pim.stars.turn.api.Race;

public interface ReportCreator {

	public ReportTypeBuilder start(Game game, Race race);

	public static interface ReportTypeBuilder {

		public ReportArgumentBuilder type(Class<?> reportClass);

		public ReportArgumentBuilder type(String reportClassName);
	}

	public static interface ReportArgumentBuilder {

		public ReportArgumentBuilder addArguments(String... arguments);

		public void create();

	}
}

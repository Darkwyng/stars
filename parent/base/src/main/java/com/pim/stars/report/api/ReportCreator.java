package com.pim.stars.report.api;

import com.pim.stars.game.api.Game;
import com.pim.stars.race.api.Race;

public interface ReportCreator {

	public ReportTypeBuilder start(Game game, Race race);

	public ReportTypeBuilder start(Game game, String raceId);

	public static interface ReportTypeBuilder {

		public ReportBundleBuilder type(Class<?> reportClass);

		public ReportBundleBuilder type(String reportClassName);
	}

	public static interface ReportBundleBuilder {

		public ReportArgumentBuilder bundle(String bundleName);
	}

	public static interface ReportArgumentBuilder {

		public ReportArgumentBuilder addArguments(String... arguments);

		public void create(); // TODO 3: add type last, so that it is not forgotten

	}
}

package com.pim.stars.report.api;

import com.pim.stars.game.api.Game;
import com.pim.stars.race.api.Race;

public interface ReportCreator {

	public ReportTypeBuilder start(Game game, Race race);

	public ReportTypeBuilder start(Game game, String raceId);

	public static interface ReportTypeBuilder {

		public ReportBundleBuilder type(Class<?> reportClass);

		public ReportBundleBuilder type(String reportClassName);

		public ReportArgumentBuilder typeAndBundle(Class<?> reportClass);
	}

	public static interface ReportBundleBuilder {

		public ReportArgumentBuilder bundle(String bundleName);
	}

	public static interface ReportArgumentBuilder {

		public ReportArgumentBuilder addArguments(String... arguments);

		public void create();

	}
}

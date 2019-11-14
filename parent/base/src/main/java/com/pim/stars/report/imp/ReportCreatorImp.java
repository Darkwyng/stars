package com.pim.stars.report.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;
import com.pim.stars.game.api.Game;
import com.pim.stars.report.api.ReportCreator;
import com.pim.stars.report.imp.persistence.ReportEntity;
import com.pim.stars.report.imp.persistence.ReportRepository;
import com.pim.stars.turn.api.Race;

@Component
public class ReportCreatorImp implements ReportCreator {

	@Autowired
	private ReportRepository reportRepository;

	@Override
	public ReportTypeBuilder start(final Game game, final Race race) {
		return start(game, race.getId());
	}

	@Override
	public ReportTypeBuilder start(final Game game, final String raceId) {
		return start(game.getId(), game.getYear(), raceId);
	}

	private ReportTypeBuilder start(final String gameId, final int year, final String raceId) {
		Preconditions.checkNotNull(gameId, "gameId must not be null");
		Preconditions.checkNotNull(raceId, "raceId must not be null");

		final ReportEntity report = new ReportEntity();
		report.setGameId(gameId);
		report.setYear(year);
		report.setRaceId(raceId);

		return new ReportTypeBuilderImp(reportRepository, report);
	}

	private static class ReportTypeBuilderImp implements ReportTypeBuilder {

		private final ReportRepository reportRepository;
		private final ReportEntity report;

		public ReportTypeBuilderImp(final ReportRepository reportRepository, final ReportEntity report) {
			this.reportRepository = reportRepository;
			this.report = report;
		}

		@Override
		public ReportBundleBuilder type(final Class<?> reportClass) {
			return type(reportClass.getName());
		}

		@Override
		public ReportBundleBuilder type(final String reportClassName) {
			Preconditions.checkNotNull(reportClassName, "reportClassName must not be null");
			report.setReportClassName(reportClassName);
			return new ReportBundleBuilderImp(reportRepository, report);
		}
	}

	private static class ReportBundleBuilderImp implements ReportBundleBuilder {

		private final ReportRepository reportRepository;
		private final ReportEntity report;

		public ReportBundleBuilderImp(final ReportRepository reportRepository, final ReportEntity report) {
			this.reportRepository = reportRepository;
			this.report = report;
		}

		@Override
		public ReportArgumentBuilder bundle(final String bundleName) {
			Preconditions.checkNotNull(bundleName, "bundleName must not be null");
			report.setBundleName(bundleName);
			return new ReportArgumentBuilderImp(reportRepository, report);
		}

	}

	private static class ReportArgumentBuilderImp implements ReportArgumentBuilder {

		private final ReportRepository reportRepository;
		private final ReportEntity report;

		public ReportArgumentBuilderImp(final ReportRepository reportRepository, final ReportEntity report) {
			this.reportRepository = reportRepository;
			this.report = report;
		}

		@Override
		public ReportArgumentBuilder addArguments(final String... arguments) {
			for (final String argument : arguments) {
				report.getArguments().add(argument);
			}
			return this;
		}

		@Override
		public void create() {
			reportRepository.insert(report);
		}
	}
}
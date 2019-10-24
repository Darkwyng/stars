package com.pim.stars.report.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.report.api.ReportBuilder;
import com.pim.stars.report.imp.persistence.ReportEntity;
import com.pim.stars.report.imp.persistence.ReportRepository;
import com.pim.stars.turn.api.Race;

@Component
public class ReportBuilderImp implements ReportBuilder {

	@Autowired
	private ReportRepository reportRepository;

	@Override
	public ReportTypeBuilder create(final Game game, final Race race) {
		final ReportEntity report = new ReportEntity();
		report.setGameId(game.getId());
		report.setYear(game.getYear());
		report.setRaceId(race.getId());

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
		public ReportArgumentBuilder type(final Class<?> reportClass) {
			return type(reportClass.getName());
		}

		@Override
		public ReportArgumentBuilder type(final String reportClassName) {
			report.setReportClassName(reportClassName);
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
		public void build() {
			reportRepository.insert(report);
		}
	}
}
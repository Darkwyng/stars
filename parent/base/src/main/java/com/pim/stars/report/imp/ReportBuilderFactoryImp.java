package com.pim.stars.report.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.report.api.ReportBuilder;
import com.pim.stars.report.api.ReportBuilder.ReportBuilderFactory;
import com.pim.stars.report.imp.persistence.ReportEntity;
import com.pim.stars.report.imp.persistence.ReportRepository;

@Component
public class ReportBuilderFactoryImp implements ReportBuilderFactory {

	@Autowired
	private ReportRepository reportRepository;

	@Override
	public ReportBuilder create(final Class<?> reportClass) {
		return create(reportClass.getName());
	}

	@Override
	public ReportBuilder create(final String reportClassName) {
		return new ReportBuilderImp(reportRepository, reportClassName);
	}

	private static class ReportBuilderImp implements ReportBuilder {

		private final String reportClassName;
		private final ReportRepository reportRepository;

		public ReportBuilderImp(final ReportRepository reportRepository, final String reportClassName) {
			this.reportRepository = reportRepository;
			this.reportClassName = reportClassName;
		}

		@Override
		public void build() {
			final ReportEntity report = new ReportEntity();
			report.setReportClassName(reportClassName);
			reportRepository.insert(report);
		}
	}
}
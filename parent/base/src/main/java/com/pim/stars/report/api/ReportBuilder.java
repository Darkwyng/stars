package com.pim.stars.report.api;

public interface ReportBuilder {

	public ReportBuilder addArguments(String... arguments);

	public void build();

	public static interface ReportBuilderFactory {

		public ReportBuilder create(Class<?> reportClass);

		public ReportBuilder create(String reportClassName);
	}
}

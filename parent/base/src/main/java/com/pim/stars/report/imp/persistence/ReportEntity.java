package com.pim.stars.report.imp.persistence;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ReportEntity {

	private String reportClassName;

	public String getReportClassName() {
		return reportClassName;
	}

	public void setReportClassName(final String className) {
		this.reportClassName = className;
	}
}

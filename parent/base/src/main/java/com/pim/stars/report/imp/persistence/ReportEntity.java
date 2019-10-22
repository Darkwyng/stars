package com.pim.stars.report.imp.persistence;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ReportEntity {

	private String reportClassName;
	private Collection<String> arguments = new ArrayList<>();

	public String getReportClassName() {
		return reportClassName;
	}

	public void setReportClassName(final String className) {
		this.reportClassName = className;
	}

	public Collection<String> getArguments() {
		return arguments;
	}

	public void setArguments(final Collection<String> arguments) {
		this.arguments = arguments;
	}
}

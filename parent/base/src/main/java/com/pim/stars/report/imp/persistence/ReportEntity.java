package com.pim.stars.report.imp.persistence;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ReportEntity {

	private String gameId;
	private int year;
	private String raceId;

	private String reportClassName;
	private String bundleName;

	private Collection<String> arguments = new ArrayList<>();

	public String getGameId() {
		return gameId;
	}

	public void setGameId(final String gameId) {
		this.gameId = gameId;
	}

	public int getYear() {
		return year;
	}

	public void setYear(final int year) {
		this.year = year;
	}

	public String getRaceId() {
		return raceId;
	}

	public void setRaceId(final String raceId) {
		this.raceId = raceId;
	}

	public String getReportClassName() {
		return reportClassName;
	}

	public void setReportClassName(final String className) {
		this.reportClassName = className;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(final String bundleName) {
		this.bundleName = bundleName;

	}

	public Collection<String> getArguments() {
		return arguments;
	}

	public void setArguments(final Collection<String> arguments) {
		this.arguments = arguments;
	}
}

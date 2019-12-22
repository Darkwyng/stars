package com.pim.stars.report.imp.persistence;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document
@Getter
@Setter
@ToString
public class ReportEntity {

	private String gameId;
	private int year;
	private String raceId;

	private String reportClassName;
	private String bundleName;

	private Collection<String> arguments = new ArrayList<>();
}
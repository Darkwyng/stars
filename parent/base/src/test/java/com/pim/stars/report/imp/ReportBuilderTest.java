package com.pim.stars.report.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.report.ReportTestConfiguration;
import com.pim.stars.report.api.ReportBuilder.ReportBuilderFactory;
import com.pim.stars.report.imp.persistence.ReportEntity;
import com.pim.stars.report.imp.persistence.ReportRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ReportTestConfiguration.class)
public class ReportBuilderTest {

	@Autowired
	private ReportBuilderFactory reportBuilderFactory;
	@Autowired
	private ReportRepository reportRepository;

	@Test
	public void testThatReportCanBeCreated() {
		reportBuilderFactory.create(getClass()).addArguments("One", "Two").build();

		final List<ReportEntity> allReports = reportRepository.findAll();
		assertThat(allReports, iterableWithSize(1));

		final ReportEntity report = allReports.iterator().next();
		assertThat(report.getReportClassName(), is(getClass().getName()));
		assertThat(report.getArguments(), containsInAnyOrder("One", "Two"));
	}
}

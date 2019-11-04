package com.pim.stars.report;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.report.api.ReportCreator;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ReportTestConfiguration.class)
public class ReportConfigurationTest {

	@Autowired
	private ReportCreator reportCreator;

	@Test
	public void testThatApplicationContextStarts() {
		assertThat(reportCreator, not(nullValue()));
	}
}

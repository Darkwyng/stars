package com.pim.stars.report.imp.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsIterableContaining.hasItems;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ReportEntityTest {

	@Test
	public void testEntityGettersReturnCorrectData() {
		final ReportEntity entity = new ReportEntity();

		entity.setGameId("48");
		entity.setYear(47);
		entity.setRaceId("46");
		entity.setReportClassName("45");
		entity.setBundleName("44");
		entity.setArguments(Arrays.asList("43", "42"));

		assertAll(entity.toString(), () -> assertThat(entity.getGameId(), is("48")), //
				() -> assertThat(entity.getYear(), is(47)), //
				() -> assertThat(entity.getRaceId(), is("46")), //
				() -> assertThat(entity.getReportClassName(), is("45")), //
				() -> assertThat(entity.getBundleName(), is("44")), //
				() -> assertThat(entity.getArguments(), hasItems("43", "42")));
	}
}
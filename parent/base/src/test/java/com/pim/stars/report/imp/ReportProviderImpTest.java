package com.pim.stars.report.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pim.stars.game.api.Game;
import com.pim.stars.report.api.ReportProvider.MessageReport;
import com.pim.stars.report.imp.persistence.ReportEntity;
import com.pim.stars.report.imp.persistence.ReportRepository;
import com.pim.stars.turn.api.Race;

public class ReportProviderImpTest {

	@Mock
	private ReportRepository reportRepository;

	@InjectMocks
	private ReportProviderImp testee;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		final ReportEntity one = new ReportEntity();
		one.setReportClassName("Type1");
		one.setBundleName("com.pim.stars.report.first.messages");
		one.getArguments().add("Unused argument");

		final ReportEntity two = new ReportEntity();
		two.setReportClassName("Type2");
		two.setBundleName("com.pim.stars.report.second.messages");
		two.getArguments().add("John Smith");
		two.getArguments().add("Hi");

		when(reportRepository.findByGameIdAndYearAndRaceId("myGameId", 2501, "myRaceId"))
				.thenReturn(Arrays.asList(one, two));
	}

	@Test
	public void testThatProviderConvertsDataFromRepository() {
		final List<MessageReport> allReports = testee
				.getReports(mockGame("myGameId", 2501), mockRace("myRaceId"), Locale.CHINA)
				.collect(Collectors.toList());
		assertThat(allReports, not(empty()));
		for (final MessageReport report : allReports) {

			switch (report.getType()) {
			case "Type1":
				assertThat(report.getMessage(), is("Hello, world. This is an Ümläut."));
				break;
			case "Type2":
				assertThat(report.getMessage(), is("Hello, John Smith. You said 'Hi'."));
				break;
			default:
				fail("Unknown type: " + report.getType());
			}
		}
	}

	@Test
	public void testThatProviderManagesEmptyList() {
		final List<MessageReport> reports = testee
				.getReports(mockGame("myOtherGameId", 2501), mockRace("myRaceId"), Locale.CHINA)
				.collect(Collectors.toList());
		assertThat(reports, empty());
	}

	private Game mockGame(final String gameId, final int year) {
		final Game game = mock(Game.class);
		when(game.getId()).thenReturn(gameId);
		when(game.getYear()).thenReturn(year);
		return game;
	}

	private Race mockRace(final String raceId) {
		final Race race = mock(Race.class);
		when(race.getId()).thenReturn(raceId);
		return race;
	}
}
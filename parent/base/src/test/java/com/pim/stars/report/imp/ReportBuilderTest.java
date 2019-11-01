package com.pim.stars.report.imp;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.game.api.Game;
import com.pim.stars.report.ReportTestConfiguration;
import com.pim.stars.report.api.ReportCreator;
import com.pim.stars.report.imp.persistence.ReportEntity;
import com.pim.stars.report.imp.persistence.ReportRepository;
import com.pim.stars.turn.api.Race;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ReportTestConfiguration.class)
public class ReportBuilderTest {

	private static final String BUNDLE_NAME = "com.pim.stars.report.imp.messages";

	@Autowired
	private ReportCreator reportCreator;
	@Autowired
	private ReportRepository reportRepository;

	@BeforeEach
	public void setUp() {
		reportRepository.deleteAll();
	}

	@Test
	public void testThatReportCanBeCreatedAndRead() {
		reportCreator.start(mockGame("myGameId", 2501), mockRace("my race id")).type(getClass()).bundle(BUNDLE_NAME)
				.addArguments("One", "Two").create();

		final List<ReportEntity> allReports = reportRepository.findAll();
		assertThat(allReports, iterableWithSize(1));

		final ReportEntity report = allReports.iterator().next();
		assertAll(() -> assertThat(report.getReportClassName(), is(getClass().getName())),
				() -> assertThat(report.getArguments(), containsInAnyOrder("One", "Two")),
				() -> assertThat(report.getGameId(), is("myGameId")), //
				() -> assertThat(report.getYear(), is(2501)), //
				() -> assertThat(report.getRaceId(), is("my race id")));
	}

	@Test
	public void testThatReportCanFoundByGameAndRace() {
		final String gameId = "myGameId";
		final int year = 2501;
		final String raceId = "my race id";

		// Same ids and year:
		reportCreator.start(mockGame(gameId, year), mockRace(raceId)).type("Type1").bundle(BUNDLE_NAME).create();
		reportCreator.start(mockGame(gameId, year), mockRace(raceId)).type("Type2").bundle(BUNDLE_NAME).create();

		// Other data:
		reportCreator.start(mockGame("myOtherGameId", year), mockRace(raceId)).type("Type3").bundle(BUNDLE_NAME)
				.create();
		reportCreator.start(mockGame(gameId, 2502), mockRace(raceId)).type("Type4").bundle(BUNDLE_NAME).create();
		reportCreator.start(mockGame(gameId, year), mockRace("my other race id")).type("Type5").bundle(BUNDLE_NAME)
				.create();

		assertAll(() -> assertThat(getStoredReportTypes(gameId, year, raceId), containsInAnyOrder("Type1", "Type2")),
				() -> assertThat(getStoredReportTypes("myOtherGameId", year, raceId), containsInAnyOrder("Type3")),
				() -> assertThat(getStoredReportTypes(gameId, 2502, raceId), containsInAnyOrder("Type4")),
				() -> assertThat(getStoredReportTypes(gameId, year, "my other race id"), containsInAnyOrder("Type5")));
	}

	private List<String> getStoredReportTypes(final String gameId, final int year, final String raceId) {
		return reportRepository.findByGameIdAndYearAndRaceId(gameId, year, raceId).stream()
				.map(ReportEntity::getReportClassName).collect(Collectors.toList());
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

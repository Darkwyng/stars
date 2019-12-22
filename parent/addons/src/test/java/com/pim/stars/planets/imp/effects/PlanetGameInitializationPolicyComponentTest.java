package com.pim.stars.planets.imp.effects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableWithSize.iterableWithSize;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.game.api.Game;
import com.pim.stars.game.api.GameInitializationData;
import com.pim.stars.planets.PlanetTestConfiguration;
import com.pim.stars.planets.api.Planet;
import com.pim.stars.planets.api.PlanetProvider;
import com.pim.stars.planets.api.extensions.GameInitializationDataNumberOfPlanets;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = PlanetTestConfiguration.WithPersistence.class)
public class PlanetGameInitializationPolicyComponentTest {

	@Autowired
	private PlanetGameInitializationPolicy testee;
	@Autowired
	private PlanetProvider planetProvider;

	@MockBean
	private GameInitializationDataNumberOfPlanets gameInitializationDataNumberOfPlanets;
	@MockBean
	private DataExtender dataExtender;

	@Test
	public void testThatPlanetsArePersistedDuringInitialization() {
		final Game game = mock(Game.class);
		when(game.getId()).thenReturn("theId");
		when(game.getYear()).thenReturn(2501);

		final GameInitializationData data = mock(GameInitializationData.class);

		when(dataExtender.extendData(any())).thenAnswer(returnsFirstArg());
		when(gameInitializationDataNumberOfPlanets.getValue(data)).thenReturn(3);

		testee.initializeGame(game, data);

		final List<Planet> persistedPlanets = planetProvider.getPlanetsByGame(game).collect(Collectors.toList());
		assertThat(persistedPlanets, iterableWithSize(3));
	}
}

package com.pim.stars.location.imp;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.number.OrderingComparison.greaterThanOrEqualTo;
import static org.hamcrest.number.OrderingComparison.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pim.stars.game.api.Game;
import com.pim.stars.location.api.Location;
import com.pim.stars.location.api.LocationManager;
import com.pim.stars.location.api.LocationProvider;
import com.pim.stars.location.api.UniverseSizeProvider;
import com.pim.stars.location.api.UniverseSizeProvider.UniverseSize;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class LocationInitializerImpTest {

	@Mock
	private UniverseSizeProvider universeSizeProvider;
	@Mock
	private LocationManager locationManager;
	@Mock
	private LocationProvider locationProvider;

	@Mock
	private Game game;

	@InjectMocks
	private LocationInitializerImp testee;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		when(universeSizeProvider.getUniverseSize(any())).thenReturn(new UniverseSizeImp(100, 200, 300, 400));
		when(locationProvider.getLocationByCoordinates(any(), anyInt(), anyInt())).thenAnswer(inv -> LocationImp.Builder
				.builder(inv.getArgument(1, Integer.class), inv.getArgument(2, Integer.class)).build());
	}

	@Test
	public void test() {
		final List<Object> objects = Arrays.asList(new Object(), "two", Integer.valueOf(3));
		testee.initializeRandomLocations(game, objects);

		final ArgumentCaptor<Location> captor = ArgumentCaptor.forClass(Location.class);
		for (final Object object : objects) {
			verify(locationManager).createLocationHolder(eq(game), eq(object), captor.capture());
		}
		final List<Location> allLocations = captor.getAllValues();
		assertThat(allLocations, hasSize(objects.size()));
		allLocations.stream().forEach(location -> {
			assertAll(() -> assertThat(location.getX(), allOf(greaterThanOrEqualTo(100), lessThanOrEqualTo(300))),
					() -> assertThat(location.getY(), allOf(greaterThanOrEqualTo(200), lessThanOrEqualTo(400))));
		});
	}
}

@Getter
@AllArgsConstructor
class UniverseSizeImp implements UniverseSize {

	private final int minX;
	private final int minY;
	private final int maxX;
	private final int maxY;
}

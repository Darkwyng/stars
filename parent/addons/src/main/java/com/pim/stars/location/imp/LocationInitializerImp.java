package com.pim.stars.location.imp;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.location.api.Location;
import com.pim.stars.location.api.LocationInitializer;
import com.pim.stars.location.api.LocationManager;
import com.pim.stars.location.api.LocationProvider;
import com.pim.stars.location.api.UniverseSizeProvider;
import com.pim.stars.location.api.UniverseSizeProvider.UniverseSize;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Component
public class LocationInitializerImp implements LocationInitializer {

	@Autowired
	private UniverseSizeProvider universeSizeProvider;
	@Autowired
	private LocationManager locationManager;
	@Autowired
	private LocationProvider locationProvider;

	final Random random = new Random();

	@Override
	public void initializeRandomLocations(final Game game, final Collection<?> locationHolders) {
		// Load date:
		final UniverseSize universeSize = universeSizeProvider.getUniverseSize(game);
		final int width = universeSize.getMaxX() - universeSize.getMinX() + 1;
		final int height = universeSize.getMaxY() - universeSize.getMinY() + 1;

		// Choose coordinates:
		final Set<Coordinates> coordinatesSet = new HashSet<>();
		while (coordinatesSet.size() < locationHolders.size()) {
			final int x = random.nextInt(width);
			final int y = random.nextInt(height);
			coordinatesSet.add(new Coordinates(x, y)); // Being a HashSet, duplicate locations will not be added, but will replace the first instance
		}

		// Call the locationManager to store the location for each object:
		final Iterator<Coordinates> locationIterator = coordinatesSet.iterator();
		locationHolders.stream().forEach(object -> {
			final Coordinates coordinates = locationIterator.next();
			final Location location = locationProvider.getLocationByCoordinates(game,
					universeSize.getMinX() + coordinates.getX(), universeSize.getMinY() + coordinates.getY());
			locationManager.createLocationHolder(game, object, location);
		});
	}

	@Getter
	@AllArgsConstructor
	@EqualsAndHashCode
	private class Coordinates {

		private final int x, y;
	}
}
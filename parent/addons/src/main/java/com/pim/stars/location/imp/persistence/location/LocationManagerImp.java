package com.pim.stars.location.imp.persistence.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.location.api.Location;
import com.pim.stars.location.api.LocationManager;

@Component
public class LocationManagerImp implements LocationManager {

	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private LocationHolderDefinitionResolver locationHolderDefinitionResolver;

	@Override
	public void createLocationHolder(final Game game, final Object newLocationHolder, final Location location) {
		saveEntity(game, newLocationHolder, location);
	}

	@Override
	public void moveLocationHolder(final Game game, final Object locationHolder, final Location newLocation) {
		saveEntity(game, locationHolder, newLocation);
	}

	@Override
	public void deleteLocationHolder(final Game game, final Object locationHolder) {
		locationRepository.deleteById(getEntityId(game, locationHolder));
	}

	private void saveEntity(final Game game, final Object locationHolder, final Location location) {
		final LocationEntityId entityId = getEntityId(game, locationHolder);
		final LocationEntity entity = new LocationEntity(entityId,
				new LocationEntityCoordinates(location.getX(), location.getY()));

		locationRepository.save(entity); // will insert or update
	}

	private LocationEntityId getEntityId(final Game game, final Object locationHolder) {
		return locationHolderDefinitionResolver.unwrapLocationHolder(locationHolder,
				(type, id) -> new LocationEntityId(game.getId(), type, id));
	}
}
package com.pim.stars.location.imp.persistence;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.pim.stars.game.api.Game;
import com.pim.stars.location.api.Location;
import com.pim.stars.location.api.LocationProvider;
import com.pim.stars.location.imp.LocationImp;
import com.pim.stars.location.imp.LocationImp.Builder;

@Component
public class LocationProviderImp implements LocationProvider {

	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private LocationHolderDefinitionResolver locationHolderDefinitionResolver;

	@Override
	public Location getLocationByCoordinates(final Game game, final int x, final int y) {
		final Collection<LocationEntity> entities = locationRepository.findByGameIdAndCoordinates(game.getId(), x, y); // TODO 5: lazy loading would be approprate here
		if (entities.isEmpty()) {
			return LocationImp.Builder.builder(x, y).build();
		} else {
			return mapEntitiesToLocation(entities);
		}
	}

	@Override
	public Stream<Location> getLocations(final Game game) {
		return locationRepository.findByGameId(game.getId()).stream()
				.collect(Collectors.groupingBy(entity -> entity.getCoordinates())).values().stream()
				.map(this::mapEntitiesToLocation);
	}

	@Override
	public Location getLocationByHolder(final Game game, final Object locationHolder) {
		final Collection<LocationEntity> entities = locationHolderDefinitionResolver.unwrapLocationHolder(
				locationHolder,
				(type, id) -> locationRepository.findAllInSameLocation(mongoTemplate, game.getId(), type, id));
		return mapEntitiesToLocation(entities);
	}

	private Location mapEntitiesToLocation(final Collection<LocationEntity> entities) {
		final LocationEntityCoordinates coordinatesOfAllEntities = entities.iterator().next().getCoordinates();

		final Builder builder = LocationImp.Builder.builder(coordinatesOfAllEntities.getX(),
				coordinatesOfAllEntities.getY());

		entities.stream().forEach(entity -> builder.add(entity.getEntityId().getLocationHolderType(),
				entity.getEntityId().getLocationHolderId()));

		return builder.build();
	}

	@Override
	public Location reloadLocation(final Game game, final Location location) {
		return getLocationByCoordinates(game, location.getX(), location.getY());
	}
}
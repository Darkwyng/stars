package com.pim.stars.location.imp;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

import com.pim.stars.game.api.Game;
import com.pim.stars.location.api.Location;
import com.pim.stars.location.api.policies.LocationHolderDefinition;

import lombok.Getter;
import lombok.ToString;

@ToString(of = { "x", "y" })
public class LocationImp implements Location {

	@Getter
	private int x;
	@Getter
	private int y;

	private final Map<String, Collection<String>> objects = new HashMap<>();

	@Override
	public double getDistance(final Location other) {
		return Math.sqrt(getDistanceForComparison(other));
	}

	@Override
	public int getDistanceForComparison(final Location other) {
		return (int) (Math.pow(getX() - other.getX(), 2) + Math.pow(getY() - other.getY(), 2));
	}

	@Override
	public <T> Stream<T> getLocationHoldersByType(Game game, final LocationHolderDefinition<T> definition) {
		return objects.getOrDefault(definition.getLocationHolderType(), Collections.emptyList()).stream()
				.map(id -> definition.toObject(game, id));
	}

	public static class Builder {

		LocationImp location = new LocationImp();

		public static Builder builder(final int x, final int y) {
			final Builder result = new Builder();
			result.location.x = x;
			result.location.y = y;
			return result;
		}

		public Builder add(final String type, final String id) {
			location.objects.computeIfAbsent(type, key -> new HashSet<>());
			location.objects.get(type).add(id);
			return this;
		}

		public Location build() {
			return location;
		}

		// For lazy loading of objects in a location - the coordinates would also have to be loaded lazily...
		//		private Supplier<Map<String, Collection<String>>> getMapSupplier(
		//				final Supplier<Collection<LocationEntity>> entitySupplier) {
		//			return () -> {
		//				final Map<String, Collection<String>> map = new HashMap<>();
		//
		//				entitySupplier.get().stream().map(LocationEntity::getEntityId).forEach(entityId -> {
		//					final String type = entityId.getLocationHolderType();
		//					final String id = entityId.getLocationHolderId();
		//
		//					map.computeIfAbsent(type, key -> new HashSet<>());
		//					map.get(type).add(id);
		//				});
		//
		//				return map;
		//			};
		//		}
	}

	@Override
	public boolean isSameLocation(final Location other) {
		return (other != null) && (other.getX() == x) && (other.getY() == y);
	}
}
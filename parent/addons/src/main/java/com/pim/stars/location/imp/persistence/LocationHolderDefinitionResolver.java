package com.pim.stars.location.imp.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pim.stars.location.api.policies.LocationHolderDefinition;

@Component
public class LocationHolderDefinitionResolver {

	@Autowired(required = false)
	private final List<LocationHolderDefinition<?>> locationHolderDefinitions = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public <T, R> R unwrapLocationHolder(final T locationHolder, final BiFunction<String, String, R> consumer) {
		final LocationHolderDefinition<T> definition = (LocationHolderDefinition<T>) locationHolderDefinitions.stream()
				.filter(candidate -> candidate.matches(locationHolder)).findAny()
				.orElseThrow(() -> new IllegalStateException(
						"No " + LocationHolderDefinition.class.getSimpleName() + " was found for " + locationHolder));
		return consumer.apply(definition.getLocationHolderType(), definition.getLocationHolderId(locationHolder));
	}
}

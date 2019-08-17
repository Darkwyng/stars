package com.pim.stars.turn.api.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.TurnCreator;
import com.pim.stars.turn.api.policies.GameEntityTransformer;

public abstract class CollectionGameEntityTransformer<E extends Entity<?>, T extends Entity<T>>
		implements GameEntityTransformer<E, Collection<T>> {

	@Autowired
	private TurnCreator turnCreator;

	@Override
	public abstract DataExtensionPolicy<E, Collection<T>> getExtensionToTransform();

	@Override
	public Collection<DataExtensionTransformer<Collection<T>, ?>> getDataExtensionTransformers() {
		return Collections.singleton(new DataExtensionTransformer<Collection<T>, Collection<T>>() {

			@SuppressWarnings({ "unchecked" })
			@Override
			public Collection<T> transform(final Collection<T> input, final Race race) {
				return input.stream().map(gameEntity -> turnCreator.transformGameEntity(gameEntity, race))
						.filter(Optional::isPresent).map(optional -> (T) optional.get())
						.collect(Collectors.toCollection(ArrayList::new));
			}

			@Override
			public DataExtensionPolicy<E, Collection<T>> getExtensionToStoreTo() {
				return getExtensionToTransform();
			}
		});
	}
}
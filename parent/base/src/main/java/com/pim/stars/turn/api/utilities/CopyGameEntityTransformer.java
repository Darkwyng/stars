package com.pim.stars.turn.api.utilities;

import java.util.Collection;
import java.util.Collections;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.policies.GameEntityTransformer;

public abstract class CopyGameEntityTransformer<E extends Entity<?>, T> implements GameEntityTransformer<E, T> {

	@Override
	public Collection<DataExtensionTransformer<T, ?>> getDataExtensionTransformers() {
		return Collections.singleton(new DataExtensionTransformer<T, T>() {

			@Override
			public T transform(final T input, final Race race) {
				return input;
			}

			@Override
			public DataExtensionPolicy<E, T> getExtensionToStoreTo() {
				return getExtensionToTransform();
			}
		});
	}
}
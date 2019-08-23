package com.pim.stars.turn.api.policies;

import java.util.Collection;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.turn.api.TurnCreator.TurnTransformationContext;

/**
 * This interface determines for a given input entity,
 * <li>which of its attributes it transforms (returned by {@link #getExtensionToTransform()})
 * <li>how it is transformed (by the result of {@link #getDataExtensionTransformers()} - the attribute will be written
 * multiple times, if the collection contains multiple entries)
 * <li>where the result of the transformation will be stored ({@link DataExtensionTransformer#getExtensionToStoreTo()})
 */
public interface GameEntityTransformer<E extends Entity<?>, I> {

	public default Class<E> getGameEntityClassToTransform() {
		return getExtensionToTransform().getEntityClass();
	}

	public DataExtensionPolicy<E, I> getExtensionToTransform();

	public Collection<DataExtensionTransformer<I, ?>> getDataExtensionTransformers();

	public interface DataExtensionTransformer<I, O> {

		public O transform(I input, TurnTransformationContext context);

		public DataExtensionPolicy<?, O> getExtensionToStoreTo();
	}
}

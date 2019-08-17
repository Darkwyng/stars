package com.pim.stars.dataextension.api.policies;

import java.util.Optional;

import com.pim.stars.dataextension.api.Entity;

public interface DataExtensionPolicy<E extends Entity<?>, T> {

	/**
	 * This suffix will be cut off when the key for a data extension is chosen, so that e.g.
	 * "GamePlanetCollectionDataExtensionPolicy" has the key "GamePlanetCollection".
	 */
	public static final String IMPLEMENTATION_SUFFIX = "DataExtensionPolicy";

	/**
	 * @return an interface extending <code>Entity</code> for which this policy is valid
	 */
	public Class<E> getEntityClass();

	/**
	 * @return the key to use when the storing the value. It must be unique for the <code>Entity</code>-type.
	 */
	public default String getKey() {
		final String result = getClass().getSimpleName();
		if (result.endsWith(IMPLEMENTATION_SUFFIX)) {
			return result.substring(0, result.length() - IMPLEMENTATION_SUFFIX.length());
		}
		return result;
	}

	/**
	 * @return the default value to set when a new instance of the <code>Entity</code> is created.
	 */
	public Optional<? extends T> getDefaultValue();

	/**
	 * @param entity the <code>Entity</code> which should have been extended by this policy
	 * @return the value currently stored; maybe null
	 */
	@SuppressWarnings("unchecked")
	public default T getValue(final E entity) {
		return (T) entity.get(getKey());
	}

	/**
	 * @param entity   the <code>Entity</code> which should have been extended by this policy
	 * @param newValue the value to set; maybe null
	 */
	public default void setValue(final E entity, final T newValue) {
		entity.set(getKey(), newValue);
	}
}
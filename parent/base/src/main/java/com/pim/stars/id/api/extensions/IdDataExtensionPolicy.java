package com.pim.stars.id.api.extensions;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.id.api.IdCreator;

public abstract class IdDataExtensionPolicy<E extends Entity<?>> implements DataExtensionPolicy<E, String> {

	@Autowired
	private IdCreator idCreator;

	@Override
	public Optional<? extends String> getDefaultValue() {
		return Optional.of(idCreator.createId());
	}
}
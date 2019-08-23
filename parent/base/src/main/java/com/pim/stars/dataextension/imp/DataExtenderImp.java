package com.pim.stars.dataextension.imp;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.dataextension.api.DataExtender;
import com.pim.stars.dataextension.api.DataExtensionPolicyProvider;
import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;

public class DataExtenderImp implements DataExtender {

	@Autowired
	private DataExtensionPolicyProvider dataExtensionPolicyProvider;

	@Override
	public void extendData(final Entity<?> entity) {

		final Collection<DataExtensionPolicy<Entity<?>, ?>> policiesToUse = dataExtensionPolicyProvider
				.getDataExtensionPoliciesForEntity(entity);

		policiesToUse.stream().forEach(policy -> {

			final Optional<?> defaultValue = policy.getDefaultValue();

			if (defaultValue.isPresent()) {
				entity.set(policy.getKey(), defaultValue.get());
			}
		});
	}
}
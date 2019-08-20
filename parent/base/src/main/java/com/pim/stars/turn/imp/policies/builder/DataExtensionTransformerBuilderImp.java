package com.pim.stars.turn.imp.policies.builder;

import java.util.function.BiFunction;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.policies.GameEntityTransformer;
import com.pim.stars.turn.api.policies.builder.GameToTurnTransformerBuilder.DataExtensionTransformerBuilder;
import com.pim.stars.turn.api.policies.builder.GameToTurnTransformerBuilder.GameEntityTransformerBuilder;

public class DataExtensionTransformerBuilderImp<E extends Entity<?>, I, O>
		implements DataExtensionTransformerBuilder<E, I, O> {

	private final GameToTurnTransformerBuilderImp baseBuilder;
	private final GameEntityTransformerBuilderImp<E, I> parentBuilder;

	private final BiFunction<I, Race, O> extensionTransformerFunction;
	private DataExtensionPolicy<?, ?> extensionToStoreTo;

	public DataExtensionTransformerBuilderImp(final GameToTurnTransformerBuilderImp baseBuilder,
			final GameEntityTransformerBuilderImp<E, I> parentBuilder,
			final BiFunction<I, Race, O> extensionTransformerFunction,
			final DataExtensionPolicy<E, I> extensionToStoreTo) {
		this.baseBuilder = baseBuilder;
		this.parentBuilder = parentBuilder;
		this.extensionTransformerFunction = extensionTransformerFunction;
		this.extensionToStoreTo = extensionToStoreTo;
	}

	@Override
	public <E2 extends Entity<?>> GameEntityTransformerBuilder<E, I> storeTo(
			final Class<? extends DataExtensionPolicy<E2, O>> extensionToStoreToClass) {
		extensionToStoreTo = baseBuilder.getSingleExtensionBean(extensionToStoreToClass);

		return and();
	}

	@Override
	public GameEntityTransformerBuilder<E, I> and() {
		parentBuilder.addDataExtensionTransformer(extensionToStoreTo, extensionTransformerFunction);

		return parentBuilder;
	}

	@Override
	public GameEntityTransformer<E, I> build() {
		return and().build();
	}
}

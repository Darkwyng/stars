package com.pim.stars.turn.imp.policies.builder;

import java.util.ArrayList;
import java.util.Collection;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.turn.api.TurnCreator.TurnTransformationContext;
import com.pim.stars.turn.api.policies.GameEntityTransformer;
import com.pim.stars.turn.api.policies.GameEntityTransformer.DataExtensionTransformer;
import com.pim.stars.turn.api.policies.builder.GameToTurnTransformerBuilder.DataExtensionTransformerBuilder;
import com.pim.stars.turn.api.policies.builder.GameToTurnTransformerBuilder.ExtensionToTurnTransformer;
import com.pim.stars.turn.api.policies.builder.GameToTurnTransformerBuilder.GameEntityTransformerBuilder;

public class GameEntityTransformerBuilderImp<E extends Entity<?>, I> implements GameEntityTransformerBuilder<E, I> {

	private final GameToTurnTransformerBuilderImp baseBuilder;
	private final DataExtensionPolicy<E, I> extensionToTransform;
	private final Collection<DataExtensionTransformer<I, ?>> extensionTransformerCollection = new ArrayList<>();

	public GameEntityTransformerBuilderImp(final GameToTurnTransformerBuilderImp baseBuilder,
			final DataExtensionPolicy<E, I> extensionToTransform) {
		this.baseBuilder = baseBuilder;
		this.extensionToTransform = extensionToTransform;
	}

	@Override
	public <O> DataExtensionTransformerBuilder<E, I, O> transform(
			final ExtensionToTurnTransformer<I, O> extensionTransformerFunction) {
		return new DataExtensionTransformerBuilderImp<E, I, O>(baseBuilder, this, extensionTransformerFunction,
				extensionToTransform);
	}

	@Override
	public DataExtensionTransformerBuilder<E, I, I> copyAll() {
		return transform((input, context) -> input);
	}

	@Override
	public GameEntityTransformer<E, I> build() {
		return new GameEntityTransformer<E, I>() {

			@Override
			public DataExtensionPolicy<E, I> getExtensionToTransform() {
				return extensionToTransform;
			}

			@Override
			public Collection<DataExtensionTransformer<I, ?>> getDataExtensionTransformers() {
				return extensionTransformerCollection;
			}
		};
	}

	protected void addDataExtensionTransformer(final DataExtensionPolicy<?, ?> extensionToStoreTo,
			final ExtensionToTurnTransformer<I, ?> extensionTransformerFunction) {
		extensionTransformerCollection.add(new DataExtensionTransformer<I, Object>() {

			@Override
			public Object transform(final I input, final TurnTransformationContext context) {
				return extensionTransformerFunction.transform(input, context);
			}

			@SuppressWarnings("unchecked") // because in the collection of transformers there may be outputs of different types
			@Override
			public DataExtensionPolicy<?, Object> getExtensionToStoreTo() {
				return (DataExtensionPolicy<?, Object>) extensionToStoreTo;
			}
		});
	}
}
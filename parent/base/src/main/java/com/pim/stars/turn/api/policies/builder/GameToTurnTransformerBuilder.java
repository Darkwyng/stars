package com.pim.stars.turn.api.policies.builder;

import java.util.Collection;
import java.util.function.BiFunction;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.TurnCreator.TurnTransformationContext;
import com.pim.stars.turn.api.policies.GameEntityTransformer;
import com.pim.stars.turn.api.policies.TurnEntityCreator;

public interface GameToTurnTransformerBuilder {

	public <E extends Entity<?>> TurnEntityCreatorBuilder<E> transformEntity(Class<E> entityToTransformClass);

	public <E extends Entity<?>, I> GameEntityTransformerBuilder<E, I> transformExtension(
			Class<? extends DataExtensionPolicy<E, I>> extensionToTransformClass);

	public <E extends Entity<?>, I extends Entity<?>> GameEntityTransformerBuilder<E, Collection<I>> transformEntityCollectionExtension(
			Class<? extends DataExtensionPolicy<E, Collection<I>>> extensionToTransformClass);

	public interface TurnEntityCreatorBuilder<E extends Entity<?>> {

		public TurnEntityCreator<E> build(BiFunction<E, Race, Entity<?>> createTurnEntityFunction);
	}

	public interface GameEntityTransformerBuilder<E extends Entity<?>, I> {

		public <O> DataExtensionTransformerBuilder<E, I, O> transform(
				ExtensionToTurnTransformer<I, O> extensionTransformerFunction);

		public DataExtensionTransformerBuilder<E, I, I> copyAll();

		public GameEntityTransformer<E, I> build();
	}

	public interface DataExtensionTransformerBuilder<E extends Entity<?>, I, O> {

		public <E2 extends Entity<?>> GameEntityTransformerBuilder<E, I> storeTo(
				Class<? extends DataExtensionPolicy<E2, O>> extensionToStoreToClass);

		public GameEntityTransformerBuilder<E, I> and();

		public GameEntityTransformer<E, I> build();
	}

	@FunctionalInterface
	public interface ExtensionToTurnTransformer<I, O> {

		public O transform(I input, TurnTransformationContext context);
	}
}
package com.pim.stars.turn.imp.policies.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.TurnCreator;
import com.pim.stars.turn.api.policies.builder.GameToTurnTransformerBuilder;

public class GameToTurnTransformerBuilderImp implements GameToTurnTransformerBuilder {

	@Autowired
	private DataExtensionPolicyLoader dataExtensionPolicyLoader;
	@Autowired
	private TurnCreator turnCreator;

	@Override
	public <E extends Entity<?>> TurnEntityCreatorBuilder<E> transformEntity(final Class<E> entityToTransformClass) {
		return new TurnEntityCreatorBuilderImp<>(entityToTransformClass);
	}

	@Override
	public <E extends Entity<?>, I> GameEntityTransformerBuilder<E, I> transformExtension(
			final Class<? extends DataExtensionPolicy<E, I>> extensionToTransformClass) {
		final DataExtensionPolicy<E, I> extensionToTransform = getSingleExtensionBean(extensionToTransformClass);

		return new GameEntityTransformerBuilderImp<E, I>(this, extensionToTransform);
	}

	@Override
	public <E extends Entity<?>, T extends Entity<?>> GameEntityTransformerBuilder<E, Collection<T>> transformEntityCollectionExtension(
			final Class<? extends DataExtensionPolicy<E, Collection<T>>> extensionToTransformClass) {
		final DataExtensionPolicy<E, Collection<T>> extensionToTransform = getSingleExtensionBean(
				extensionToTransformClass);

		final GameEntityTransformerBuilderImp<E, Collection<T>> builder = new GameEntityTransformerBuilderImp<E, Collection<T>>(
				this, extensionToTransform);
		builder.addDataExtensionTransformer(extensionToTransform, getCollectionTransformerFunction());
		return builder;
	}

	private <E extends Entity<?>> BiFunction<Collection<E>, Race, ?> getCollectionTransformerFunction() {
		return (final Collection<E> input, final Race race) -> input.stream()
				.map(gameEntity -> turnCreator.transformGameEntity(gameEntity, race)) //
				.filter(Optional::isPresent) //
				.map(optional -> (Entity<?>) optional.get()) //
				.collect(Collectors.toCollection(ArrayList::new));
	}

	protected <E extends Entity<?>, T> DataExtensionPolicy<E, T> getSingleExtensionBean(
			final Class<? extends DataExtensionPolicy<E, T>> extensionToTransformClass) {
		final DataExtensionPolicy<E, T> extensionToTransform = dataExtensionPolicyLoader
				.getSingleExtensionBean(extensionToTransformClass);
		return extensionToTransform;
	}
}
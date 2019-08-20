package com.pim.stars.turn.imp.policies.builder;

import java.util.function.BiFunction;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.policies.TurnEntityCreator;
import com.pim.stars.turn.api.policies.builder.GameToTurnTransformerBuilder.TurnEntityCreatorBuilder;

public class TurnEntityCreatorBuilderImp<E extends Entity<?>> implements TurnEntityCreatorBuilder<E> {

	private final Class<E> entityToTransformClass;

	public TurnEntityCreatorBuilderImp(final Class<E> entityToTransformClass) {
		this.entityToTransformClass = entityToTransformClass;
	}

	@Override
	public TurnEntityCreator<E> build(final BiFunction<E, Race, Entity<?>> createTurnEntityFunction) {
		return new TurnEntityCreator<E>() {

			@Override
			public Class<E> getEntityClass() {
				return entityToTransformClass;
			}

			@Override
			public Entity<?> createTurnEntity(final E gameEntity, final Race race) {
				return createTurnEntityFunction.apply(gameEntity, race);
			}
		};
	}
}
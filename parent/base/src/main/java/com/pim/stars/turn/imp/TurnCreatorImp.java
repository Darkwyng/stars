package com.pim.stars.turn.imp;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.dataextension.api.policies.DataExtensionPolicy;
import com.pim.stars.game.api.Game;
import com.pim.stars.turn.api.Race;
import com.pim.stars.turn.api.Turn;
import com.pim.stars.turn.api.TurnCreator;
import com.pim.stars.turn.api.policies.GameEntityTransformer;
import com.pim.stars.turn.api.policies.GameEntityTransformer.DataExtensionTransformer;
import com.pim.stars.turn.api.policies.TurnEntityCreator;
import com.pim.stars.turn.imp.policies.utilities.GameEntityTransformerMapper;
import com.pim.stars.turn.imp.policies.utilities.TurnEntityCreatorMapper;

public class TurnCreatorImp implements TurnCreator {

	@Autowired
	private GameEntityTransformerMapper gameEntityTransformerMapper;
	@Autowired
	private TurnEntityCreatorMapper turnEntityCreatorMapper;

	@Override
	public Turn createTurn(final Game game, final Race race) {

		final Optional<Entity<?>> optionalTurn = transformGameEntity(game, race);

		if (optionalTurn.isPresent()) {
			// Normal case:
			return (Turn) optionalTurn.get();
		} else {
			// This can't happen, because this component already implements the TurnEntityCreator:
			throw new IllegalStateException(
					"This must not happen: The " + Game.class.getSimpleName() + " cannot be transformed.");
		}
	}

	@Override
	public Optional<Entity<?>> transformGameEntity(final Entity<?> gameEntity, final Race race) {

		final List<TurnEntityCreator<Entity<?>>> creatorList = turnEntityCreatorMapper.getCreatorForEntity(gameEntity);
		if (creatorList == null) {
			// Nothing to do, the entity should not be transformed.
			return Optional.empty();

		} else if (creatorList.size() > 1) {
			// TODO: or can/should we support more than one?
			throw new IllegalStateException("Only one " + TurnEntityCreator.class + " is supported for one "
					+ Entity.class.getSimpleName() + ", but " + creatorList.size() + " were found: "
					+ creatorList.stream().map(Object::getClass).map(Class::getName).sorted()
							.collect(Collectors.joining(", ")));

		} else {
			// Normal case: create the target entity, ...
			final TurnEntityCreator<Entity<?>> turnEntityCreator = creatorList.iterator().next();
			final Entity<?> turnEntity = turnEntityCreator.createTurnEntity(gameEntity, race);

			// ... transform its extensions...
			final List<GameEntityTransformer<Entity<?>, ?>> transformersForEntity = gameEntityTransformerMapper
					.getTransformersForEntity(gameEntity);
			for (final GameEntityTransformer<Entity<?>, ?> gameTransformer : transformersForEntity) {
				final DataExtensionPolicy<Entity<?>, ?> inputExtension = gameTransformer.getExtensionToTransform();
				final Object valueToTransform = inputExtension.getValue(gameEntity);
				gameTransformer.getDataExtensionTransformers().stream().forEach(turnTransformer -> {
					transformAndStore(race, turnEntity, valueToTransform, turnTransformer);
				});
			}

			// ... and return the new entity:
			return Optional.of(turnEntity);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" }) // because we are storing an Object in DataExtensionTransformer<?,?>
	private void transformAndStore(final Race race, final Entity<?> turnEntity, final Object valueToTransform,
			final DataExtensionTransformer turnTransformer) {
		final Object transformedValue = turnTransformer.transform(valueToTransform, race);
		turnTransformer.getExtensionToStoreTo().setValue(turnEntity, transformedValue);
	}
}
package com.pim.stars.turn.imp.policies.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.pim.stars.dataextension.api.Entity;
import com.pim.stars.turn.api.policies.GameEntityTransformer;

public class GameEntityTransformerMapper {

	@Autowired(required = false)
	private final List<GameEntityTransformer<Entity<?>, ?>> gameTransformerCollection = new ArrayList<>();

	private Map<Class<? extends Entity<?>>, List<GameEntityTransformer<Entity<?>, ?>>> transfomerMap = null;

	public List<GameEntityTransformer<Entity<?>, ?>> getTransformersForEntity(final Entity<?> gameEntity) {
		return getTransfomerMap().getOrDefault(gameEntity.getEntityClass(), Collections.emptyList());
	}

	private Map<Class<? extends Entity<?>>, List<GameEntityTransformer<Entity<?>, ?>>> getTransfomerMap() {
		if (transfomerMap == null) {
			synchronized (this) {
				if (transfomerMap == null) {
					transfomerMap = gameTransformerCollection.stream()
							.collect(Collectors.groupingBy(GameEntityTransformer::getGameEntityClassToTransform));
				}
			}
		}
		return transfomerMap;
	}
}

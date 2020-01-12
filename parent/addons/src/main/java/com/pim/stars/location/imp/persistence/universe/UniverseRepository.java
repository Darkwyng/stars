package com.pim.stars.location.imp.persistence.universe;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pim.stars.game.api.Game;
import com.pim.stars.location.api.UniverseSizeProvider.UniverseSize;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Repository
public interface UniverseRepository extends MongoRepository<UniverseEntity, String> {

	public default UniverseSize getUniverseSize(final Game game) {
		return findById(game.getId()).map(entity -> new UniverseSizeImp(1, 1, entity.getWidth(), entity.getHeight()))
				.get();
	}

}

@Getter
@AllArgsConstructor
class UniverseSizeImp implements UniverseSize {

	private final int minX;
	private final int minY;
	private final int maxX;
	private final int maxY;
}
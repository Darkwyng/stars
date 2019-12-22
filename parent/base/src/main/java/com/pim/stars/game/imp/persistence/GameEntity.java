package com.pim.stars.game.imp.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document
@Getter
@Setter
@ToString
public class GameEntity {

	@Id
	private GameEntityId entityId = new GameEntityId();
	private boolean isLatest;
}

@Getter
@Setter
@ToString
class GameEntityId {

	private String gameId;
	private int year;
}